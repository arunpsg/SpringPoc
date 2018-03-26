/**
 * 
 */
package com.arun.myRetail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.arun.myRetail.entity.Product;

/**
 * @author Arun
 *
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
	Product findByProductId(int productId);
}
