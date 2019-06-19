package com.dh.common.exception;

/**
 * 非法参数校验
 * 
 * @author Lenovo
 *
 */
public class IllegalException extends RuntimeException {

	private static final long serialVersionUID = 3613072238231936125L;

	public IllegalException(String msg) {
		super(msg);
	}

	public IllegalException() {

	}

	public IllegalException(Throwable e) {
		super(e);
	}

	public IllegalException(String msg, Throwable e) {
		super(msg, e);
	}

}
