/**
 * 
 */
package com.arun.myRetail.exception;

/**
 * @author Arun
 *
 */
public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	
	public InvalidRequestException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.setErrorCode(errorCode);
	}

	public InvalidRequestException(String errorCode, String message) {
		super(message);
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
