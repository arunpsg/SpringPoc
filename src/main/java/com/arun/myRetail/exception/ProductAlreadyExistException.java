/**
 * 
 */
package com.arun.myRetail.exception;

/**
 * @author Arun
 *
 */
public class ProductAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;

	public ProductAlreadyExistException(String errorCode, String message) {
		super(message);
		this.setErrorCode(errorCode);
	}

	public ProductAlreadyExistException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.setErrorCode(errorCode);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
