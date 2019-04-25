package com.dh.common.enums;

/**
 * cookie枚举
 * 
 * @author Lenovo
 *
 */
public enum CookieEnum {
	/**
	 * request请求
	 */
	SESSION_ID("usersessionid");

	private String key;

	CookieEnum(String key) {
		this.key = key;

	}

	public String getKey() {
		return key;
	}

}
