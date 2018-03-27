/**
 * 
 */
package com.arun.myRetail.entity;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun
 *
 */
@Document(collection="CURRENT_PRICE")
public class CurrentPrice {

	@Field("value")
	@NumberFormat(style = Style.CURRENCY)
	@DecimalMax(value = "999.999", message = "The price value can not be more than 999.999")
	@DecimalMin(value = "1.00", message = "The price value can not be less than 1.00")
	@JsonProperty("value")
	private String value;
	
	@Pattern(regexp = "^[^0-9]+$", message = "The currencyCode can contain characters only")
	@Length( max = 3, message = "The currencyCode can contain maximum of 3 characters")
	@Field("currency_code")
	@JsonProperty("currency_code")
	private String currency_code;
	
	public CurrentPrice() {
	}
	
	public CurrentPrice(String value, String currency_code) {
		super();
		this.value = value;
		this.currency_code = currency_code;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the currency_code
	 */
	public String getCurrency_code() {
		return currency_code;
	}
	/**
	 * @param currency_code the currency_code to set
	 */
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency_code == null) ? 0 : currency_code.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrentPrice other = (CurrentPrice) obj;
		if (currency_code == null) {
			if (other.currency_code != null)
				return false;
		} else if (!currency_code.equals(other.currency_code))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
}
