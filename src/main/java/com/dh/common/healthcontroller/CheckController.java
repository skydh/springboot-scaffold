package com.dh.common.healthcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dh.common.constants.WebConstants;
import com.dh.common.response.ResponseVO;

/**
 * 监控页
 * 
 * @author Lenovo
 *
 */
@Controller
@ResponseBody
@RequestMapping(value = WebConstants.WEB_PREFIX)
public class CheckController {

	@RequestMapping("/heath_check")
	public ResponseVO<Object> checkHealth(HttpServletRequest request) {
		ResponseVO<Object> response = ResponseVO.success();
		return response;

	}

}
