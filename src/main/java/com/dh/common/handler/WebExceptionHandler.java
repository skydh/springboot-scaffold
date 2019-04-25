package com.dh.common.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dh.common.enums.ResponseCodeEnum;
import com.dh.common.exception.BusinessException;
import com.dh.common.international.InternationalUtils;
import com.dh.common.response.ResponseVO;

/**
 * 这里进行全局业务异常捕捉
 * 
 * @author Lenovo
 *
 */

@ResponseBody
@ControllerAdvice("com.dh")
public class WebExceptionHandler {

	@Autowired
	private InternationalUtils internationalUtils;

	@ExceptionHandler(value = Exception.class)
	public ResponseVO<Object> exceptionAll(Exception e) {
		e.printStackTrace();
		return ResponseVO.error(internationalUtils.convertMessage(ResponseCodeEnum.SERVER_ERROR.getMessage()),
				ResponseCodeEnum.SERVER_ERROR.getCode());

	}

	@ExceptionHandler(value = BusinessException.class)
	public ResponseVO<Object> exceptionHandler(HttpServletRequest request, BusinessException e) {
		return ResponseVO.error(internationalUtils.convertMessage(e.getMessage()), ResponseCodeEnum.BUSINESS.getCode());
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseVO<Object> handleMethodArgumentNotValidException(HttpMessageNotReadableException e) {
		return ResponseVO.error(internationalUtils.convertMessage(ResponseCodeEnum.BAD_PARAMETER.getMessage()),
				ResponseCodeEnum.BAD_PARAMETER.getCode());
	}

}