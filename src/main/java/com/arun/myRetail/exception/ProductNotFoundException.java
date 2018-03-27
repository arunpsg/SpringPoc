/**
 * 
 */
package com.arun.myRetail.exception;

/**
 * @author Arun
 *
 */
public class ProductNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	/**
	 * @param message
	 */
	public ProductNotFoundException(String errorCode, String message) {
		super(message);
		System.out.println("ProductNotFoundException message : " + message);
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 */
	public ProductNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProductNotFoundException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		System.out.println("ProductNotFoundException with cause message : " + message);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}	
}
