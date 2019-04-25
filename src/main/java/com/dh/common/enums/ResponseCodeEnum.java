package com.dh.common.enums;

/**
 * 返回状态码
 * 
 * @author Lenovo
 *
 */
public enum ResponseCodeEnum {

	/**
	 * 校验参数错误
	 * 
	 */
	ERROR_VALID(304, ""),
	/**
	 * 业务异常
	 */
	BUSINESS(305, ""),

	/**
	 * 参数错误
	 */
	BAD_PARAMETER(306, "common.system.http.error.306"),
	/**
	 * 路径没有找到
	 */
	NOT_FOUND(404, "common.system.http.error.404"),

	/**
	 * 服务器错误
	 */
	SERVER_ERROR(500, "common.system.http.error.500");

	private int code;
	private String message;

	ResponseCodeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResponseCodeEnum getEnumByCode(int code) {
		for (ResponseCodeEnum responseCodeEnum : ResponseCodeEnum.values()) {
			if (code == responseCodeEnum.code) {
				return responseCodeEnum;
			}
		}
		return null;

	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
