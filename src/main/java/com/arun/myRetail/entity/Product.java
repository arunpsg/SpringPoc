/**
 * 
 */
package com.arun.myRetail.entity;

import org.springframework.data.annotation.Id;

/**
 * @author Arun
 *
 */
public class Product {

	@Id
	private int productId;
	
	private String name;
	
	private CurrentPrice current_price;

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
