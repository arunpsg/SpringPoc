/**
 * 
 */
package com.arun.myRetail.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.arun.myRetail.entity.CurrentPrice;
import com.arun.myRetail.entity.Product;
import com.arun.myRetail.exception.ProductNotFoundException;
import com.arun.myRetail.repository.ProductRepository;
import com.google.gson.Gson;

/**
 * @author Arun
 *
 */
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService = new ProductServiceImpl();
    
    @Mock
    private RestTemplate mockRestTemplate;
    
	@Mock
	private ResponseEntity<String> mockResponseEntity;
	
	@Mock
	private ProductRepository mockProductRepository;
	
	private String productUrl = "http://redsky.target.com/v2/pdp/tcin/";
	
	private Product product, updatedProduct;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(productService, "productUrl", productUrl);
		product = getBasicProduct();
		updatedProduct = getBasicProductUpdate();
	}
	
	public Product getBasicProduct() {
		CurrentPrice current_price = new CurrentPrice("1543", "USD");
		Product product = new Product();
		product.setProductId(13860428);
		product.setCurrent_price(current_price);
		return product;
	}

	public Product getBasicProductUpdate() {
		CurrentPrice current_price1 = new CurrentPrice("2555", "USD");
		Product productUpdate = new Product();
		productUpdate.setProductId(13860428);
		productUpdate.setCurrent_price(current_price1);
		return productUpdate;
	}
	
	private URI buildURL(int productId) {
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

	@Test
	public void getProduct() throws RestClientException, URISyntaxException {
		int productId = product.getProductId();
		Gson gson = new Gson();
		//for 13860428
		String mockResponse = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"The Big Lebowski (Blu-ray)\"}}}}"; 
		ResponseEntity<String> responseMock = new ResponseEntity(mockResponse, HttpStatus.OK);
		
		when(mockProductRepository.findByProductId(productId)).thenReturn(product);
		HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        when(mockRestTemplate.exchange(
        		Mockito.eq(buildURL(productId)),
        		Mockito.eq(HttpMethod.GET), 
        		Mockito.eq(new HttpEntity<String>(headers)),
        		Mockito.eq(String.class))).thenReturn(responseMock);
		Product actual = productService.getProduct(productId);
		System.out.println("Check");
		assertSame(product, actual);
	}
	
	@Test(expected = ProductNotFoundException.class)
	public void getProductNotFound() {
		int productId = product.getProductId();
		when(mockProductRepository.findByProductId(productId)).thenReturn(null);
		productService.getProduct(productId);
	}
	
	@Test
	public void updateProduct() {
		int productId = product.getProductId();
		when(mockProductRepository.findByProductId(Mockito.anyInt())).thenReturn(product);
		when(mockProductRepository.save(Mockito.any(Product.class))).thenReturn(updatedProduct);
		Product actual = productService.updateProduct(productId, updatedProduct);
		assertThat(updatedProduct.getCurrent_price()).isEqualTo(
				actual.getCurrent_price());
	}

	@Test(expected = ProductNotFoundException.class)
	public void updateProductNotFound() {
		int productId = product.getProductId();
		when(mockProductRepository.findByProductId(productId)).thenReturn(null);
		productService.updateProduct(productId, updatedProduct);
	}

}
