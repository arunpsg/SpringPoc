/**
 * 
 */
package com.arun.myRetail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arun.myRetail.entity.Product;
import com.arun.myRetail.service.ProductService;

/**
 * @author Arun
 *
 */
@RestController
@RequestMapping(value = "/products/v1")
public class ProductController {
	
	@Autowired
	ProductService productService;

	/**
	 * Fetches Product for a given id.
	 * 
	 * @param id
	 * @return Product from DB
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProduct(@PathVariable("id") int id) {
		return productService.getProduct(id);
	}
	
	/**
	 * Updates the given product details to DB.
	 * 
	 * @param id
	 * @param Product details that are to be updated.
	 * @return Updated product
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product updateProduct(@PathVariable("id") int id, @RequestBody Product productUpdate) {
		return productService.updateProduct(id, productUpdate);
	}
	
	@RequestMapping(value="/checkApi", method= RequestMethod.GET)
	public String check() {
		return "myRetail is running";
	}
}
