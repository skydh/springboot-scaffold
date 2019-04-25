package com.dh.common.exception;

/**
 * 参数校验异常
 * 
 * @author Lenovo
 *
 */
public class VaildException extends RuntimeException {

	private static final long serialVersionUID = 3613072238231936125L;

	public VaildException(String msg) {
		super(msg);
	}

	public VaildException(Throwable e) {
		super(e);
	}

	public VaildException(String msg, Throwable e) {
		super(msg, e);
	}

}