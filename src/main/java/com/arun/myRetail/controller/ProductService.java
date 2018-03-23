/**
 * 
 */
package com.arun.myRetail.controller;

import com.arun.myRetail.entity.Product;

/**
 * @author Arun
 *
 */
public interface ProductService {

	Product updateProduct(String id, Product productUpdate);

	Product addProduct(Product product);

	Product getProduct(String id);
	
}
