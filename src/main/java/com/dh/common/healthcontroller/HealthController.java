package com.dh.common.healthcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dh.common.constants.WebConstants;
import com.dh.common.response.ResponseVO;

/**
 * 健康页
 * 
 * @author Lenovo
 *
 */
@Controller
@ResponseBody
@RequestMapping(value = WebConstants.WEB_PREFIX)
public class HealthController {

	@RequestMapping("/check_heath")
	public ResponseVO<Object> checkHealth(HttpServletRequest request) {
		String msg = "健康页检测测试";
		ResponseVO<Object> response = ResponseVO.success(msg);
		return response;

	}

}