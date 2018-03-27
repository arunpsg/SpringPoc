/**
 * 
 */
package com.arun.myRetail.entity;

import javax.validation.Valid;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun
 *
 */
@Document(collection="PRODUCT")
public class Product {

	@Field("product_id")
	@JsonProperty("productId")
	@Id
	private int productId;
	
	@Field("product_name")
	@JsonProperty("name")
	private String name;
	
	@Valid
	@JsonProperty("current_price")
	private CurrentPrice current_price;

	public Product() {
	}
	
	public Product(int productId, CurrentPrice current_price) {
		super();
		this.productId = productId;
		this.current_price = current_price;
	}
	
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the current_price
	 */
	public CurrentPrice getCurrent_price() {
		return current_price;
	}

	/**
	 * @param current_price the current_price to set
	 */
	public void setCurrent_price(CurrentPrice current_price) {
		this.current_price = current_price;
	}
	
}
