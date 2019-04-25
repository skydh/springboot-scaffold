package com.dh.common.errorcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dh.common.enums.ResponseCodeEnum;
import com.dh.common.international.InternationalUtils;
import com.dh.common.response.ResponseVO;

/**
 * 这里处理404异常，为了更友好的展示，返回
 * 
 * @author Lenovo
 *
 */
@Controller
@ResponseBody
public class WebErrorController implements ErrorController {

	@Autowired
	private InternationalUtils internationalUtils;

	@RequestMapping("/error")
	public ResponseVO<Object> handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == 404) {
			return ResponseVO.error(internationalUtils.convertMessage(ResponseCodeEnum.NOT_FOUND.getMessage()),
					ResponseCodeEnum.NOT_FOUND.getCode());
		} else {
			return ResponseVO.error(internationalUtils.convertMessage(ResponseCodeEnum.SERVER_ERROR.getMessage()),
					ResponseCodeEnum.SERVER_ERROR.getCode());
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}