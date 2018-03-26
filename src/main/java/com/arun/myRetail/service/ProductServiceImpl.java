/**
 * 
 */
package com.arun.myRetail.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.arun.myRetail.entity.Product;
import com.arun.myRetail.exception.ExternalApiCallException;
import com.arun.myRetail.exception.InvalidRequestException;
import com.arun.myRetail.exception.ProductAlreadyExistException;
import com.arun.myRetail.exception.ProductNotFoundException;
import com.arun.myRetail.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Arun
 *
 */

@Service
public class ProductServiceImpl implements ProductService {

	/*
     * Error Codes
	 *
	 * MY-RETAIL-1001 : Product Not Found Exception
	 */
	
	@Value("${myRetail.product.url}")
	private String productUrl;
	
	private static final String PRODUCT_TITLE_JSON_PATH = "/product/item/product_description/title";
	
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	private void validateProduct(int productId) {
		if (productId == 0) {
			 throw new InvalidRequestException("MY-RETAIL-1000" ,"Product id " + productId);
		 }
	}
	
	/* (non-Javadoc)
	 * @see com.arun.myRetail.service.ProductService#updateProduct(java.lang.String, com.arun.myRetail.entity.Product)
	 */
	@Override
	public Product updateProduct(int id, Product product) {
		int productId = product.getProductId();
		
		validateProduct(productId);
		
		Optional<Product> existingProductContainer = productRepository.findById(id);
		if (!existingProductContainer.isPresent()) {
			log.info("Product with id " + productId + " not found in database");
			throw new ProductNotFoundException("MY-RETAIL-1001", "No product found for product id" + productId);
		}
		Product existingProduct = existingProductContainer.get();
		log.info("Original Price %s for product Id %s : " + existingProduct.getCurrent_price() + productId);
		existingProduct.setCurrent_price(product.getCurrent_price());		
		Product updatedProduct = productRepository.save(existingProduct);
		log.info("Price after update %s for product Id %s : " + updatedProduct.getCurrent_price() + productId);
		
		return updatedProduct;
	}

	/* (non-Javadoc)
	 * @see com.arun.myRetail.service.ProductService#addProduct(com.arun.myRetail.entity.Product)
	 */
	/*@Override
	public Product addProduct(Product product) {
		int productId = product.getProductId();
		
		validateProduct(productId);
		Optional<Product> existingProductContainer = productRepository.findById(productId);
		if (!existingProductContainer.isPresent()) {
			throw new ProductAlreadyExistException("MY-RETAIL-2001", "Product with id=" + productId
					+ " already exists!");
		} else {
			productRepository.save(product);
		}
		return null;
	}*/

	/* (non-Javadoc)
	 * @see com.arun.myRetail.service.ProductService#getProduct(java.lang.String)
	 */
	@Override
	public Product getProduct(int id) {
//		productRepository.findOne(Query.query(Criteria.where("id").is(id)), Product.class, "product");
		Product product = productRepository.findByProductId(id);
		if (product == null) {
			log.error("Error: product details not found for id : " + id);
			throw new ProductNotFoundException("MY-RETAIL-1001","Product details for id=" + id
					+ " not found");
		}
		int productId = product.getProductId();
		String productNameFromTargetAPI = getProductNameFromTargetAPI(productId);
		product.setName(productNameFromTargetAPI);
		productRepository.save(product);
		return product;
	}
	
	private java.net.URI buildURL(int productId) {
		String url = productUrl + productId;

		List<String> excludes = new ArrayList<>();
		excludes.add("taxonomy");
		excludes.add("price");
		excludes.add("promotion");
		excludes.add("bulk_ship");
		excludes.add("rating_and_review_reviews");
		excludes.add("rating_and_review_statistics");
		excludes.add("question_answer_statistics");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("excludes", excludes);

		return builder.build().encode().toUri();
	}

	private String getProductNameFromTargetAPI(int productId) {
		String productTitle = null;
		String responseBody = null;
		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();
		URI builtUrl = buildURL(productId);
		
		try {
//			response = restTemplate.getForEntity(builtUrl, String.class);
			HttpHeaders headersForRestTemplate = new HttpHeaders();
			headersForRestTemplate.set("Content-Type", "application/json");
			HttpEntity<String> requestEntity = new HttpEntity<String>(headersForRestTemplate);
//			String targetUrl = "https://redsky.target.com/v2/pdp/tcin/13860428";
			response = perFormRestCall(builtUrl, requestEntity);
			HttpHeaders responseHeaders = response.getHeaders();
			HttpStatus statusCode = response.getStatusCode();
	        if (statusCode.equals(HttpStatus.MOVED_PERMANENTLY) || statusCode.equals(HttpStatus.FOUND) || statusCode.equals(HttpStatus.SEE_OTHER)) 
	        {        
	        	log.info("Redirect Status : " + statusCode.value());
	            if (responseHeaders.getLocation() != null) {
	                response = perFormRestCall (responseHeaders.getLocation(),requestEntity);
	            } else {
	            	throw new ExternalApiCallException("MY-RETAIL-1002", "product id : " + productId);
	            }
	        }
//					restTemplate.exchange(targetUrl, HttpMethod.GET, requestEntity, String.class);
			responseBody = response.getBody();
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new ProductNotFoundException("MY-RETAIL-1001", "product id : " + productId, ex);
			} if (ex.getStatusCode() == HttpStatus.FORBIDDEN) {
				throw new ExternalApiCallException("MY-RETAIL-1002", "product id : " + productId, ex);
			} 
			else {
				throw new RuntimeException("product id : " + productId, ex);
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Unknown exception while trying to get product title  for product id " + productId, e);
		}
		if (!response.getStatusCode().equals(HttpStatus.OK))
			log.error("Error ocurred while retrieving product title, status code: " + response.getStatusCode().value());
		ObjectMapper mapper = new ObjectMapper();

		try {
			JsonNode node = mapper.readTree(responseBody);
			JsonNode titleNode = node.at(PRODUCT_TITLE_JSON_PATH);
			log.info("Product Title : " + titleNode);
			productTitle = titleNode.textValue();
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Problem parsing JSON String for title : " + responseBody, e);
		} catch (IOException e) {
			throw new RuntimeException("Error when retrieving product title from string " + responseBody, e);
		}
		return productTitle;
	}
	
	private ResponseEntity<String> perFormRestCall(URI targetUrl, HttpEntity<String> requestEntity) {
        try {
        	log.info("performing Rest Call for targetUrl : " + targetUrl.toString());
        	return restTemplate.exchange(targetUrl, HttpMethod.GET, requestEntity, String.class);
//            return restTemplate.getForEntity(url.trim(), String.class);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

}
