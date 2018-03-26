/**
 * 
 */
package com.arun.myRetail.exception;

/**
 * @author Arun
 *
 */
public class ExternalApiCallException extends RuntimeException {

	private String errorCode;
	
	/**
	 * @param errorCode
	 * @param message
	 */
	public ExternalApiCallException(String errorCode, String message) {
		super(message);
		this.setErrorCode(errorCode);
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param cause
	 */
	public ExternalApiCallException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.setErrorCode(errorCode);
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
