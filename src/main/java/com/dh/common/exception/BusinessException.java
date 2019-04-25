package com.dh.common.exception;

/**
 * 这里封装统一的异常处理类 所有业务异常统一处理
 * 
 * @author yyf
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 3613072238231936125L;

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(Throwable e) {
		super(e);
	}

	public BusinessException(String msg, Throwable e) {
		super(msg, e);
	}

}
