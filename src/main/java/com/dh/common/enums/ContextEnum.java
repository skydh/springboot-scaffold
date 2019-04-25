package com.dh.common.enums;

/**
 * 需要存放到上下文的key在这里定义
 * 
 * @author Lenovo
 *
 */
public enum ContextEnum {

	/**
	 * request请求
	 */
	CONTEXT_USER("CONTEXTUSER");

	private String key;

	ContextEnum(String key) {
		this.key = key;

	}

	public String getKey() {
		return key;
	}

}
