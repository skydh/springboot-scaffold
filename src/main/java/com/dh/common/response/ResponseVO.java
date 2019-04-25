package com.dh.common.response;

import lombok.Data;
/**
 * 为了格式统一和数据验证，这里采用统一的数据结构，方便维护 考虑到id生成问题的经常调用，将其加入到spring容器中
 * 
 * @author yyf
 *
 */
@Data
public class ResponseVO<T> {
	private boolean success;
	private String responseMsg;
	private int responseCode;
	private T responseData;

	public static <T> ResponseVO<T> success() {
		ResponseVO<T> success = new ResponseVO<T>();
		success.setSuccess(true);
		success.setResponseCode(200);
		success.setResponseMsg("请求成功");
		return success;
	}

	public static <T> ResponseVO<T> success(T data) {
		ResponseVO<T> success = new ResponseVO<T>();
		success.setSuccess(true);
		success.setResponseCode(200);
		success.setResponseMsg("请求成功");
		success.setResponseData(data);
		return success;
	}

	public static <T> ResponseVO<T> error(String message, int code) {
		ResponseVO<T> success = new ResponseVO<T>();
		success.setSuccess(true);
		success.setResponseCode(code);
		success.setResponseMsg(message);
		return success;
	}

}
