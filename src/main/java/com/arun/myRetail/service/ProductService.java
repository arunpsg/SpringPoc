/**
 * 
 */
package com.arun.myRetail.service;

import com.arun.myRetail.entity.Product;

/**
 * @author Arun
 *
 */
public interface ProductService {

	Product updateProduct(int id, Product productUpdate);

	Product getProduct(int id);
	
}
