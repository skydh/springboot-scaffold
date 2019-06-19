package com.dh.common.interceptor;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dh.common.enums.IllegalEnum;
import com.dh.common.exception.BusinessException;

/**
 * 这里进行安全校验
 * 
 * @author Lenovo
 *
 */
@Component
public class IllegalResquestParamInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 获取请求所有参数，校验防止脚本注入，防止XSS漏洞
		Enumeration<?> params = request.getParameterNames();
		String paramN = null;
		while (params.hasMoreElements()) {
			paramN = (String) params.nextElement();
			String paramVale = request.getParameter(paramN);

			// 校验是否存在SQL注入信息
			if (checkSQLInject(paramVale)) {
				throw new BusinessException("request.params.has.specialCharacter");
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// DO NOTHING
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// DO NOTHING
	}

	/**
	 * 检查是否存在非法字符，防止js脚本注入
	 *
	 * @param str
	 *            被检查的字符串
	 * @return ture-字符串中存在非法字符，false-不存在非法字符
	 */
	public boolean checkSQLInject(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;// 如果传入空串则认为不存在非法字符
		}
		List<String> list = IllegalEnum.getEnumList();
		String[] inj_stra = list.toArray(new String[list.size()]);

		// 判断黑名单

		str = str.toLowerCase(); // sql不区分大小写
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.contains(inj_stra[i])) {
				return true;
			}
		}
		return false;
	}
}
