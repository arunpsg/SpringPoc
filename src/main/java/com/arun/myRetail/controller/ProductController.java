/**
 * 
 */
package com.arun.myRetail.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arun.myRetail.entity.Product;

/**
 * @author Arun
 *
 */
@RestController
@RequestMapping(value = "/products/v1")
public class ProductController {
	
	ProductService productService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProduct(@PathVariable("id") String id) {
		return productService.getProduct(id);
	}
}
