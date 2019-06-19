package com.dh.common.exception;

/**
 * redis 加锁异常
 * @author Lenovo
 *
 */
public class RedisJobException extends RuntimeException {

	private static final long serialVersionUID = 3613072238231936125L;

	public RedisJobException(String msg) {
		super(msg);
	}

	public RedisJobException(Throwable e) {
		super(e);
	}

	public RedisJobException(String msg, Throwable e) {
		super(msg, e);
	}

}
