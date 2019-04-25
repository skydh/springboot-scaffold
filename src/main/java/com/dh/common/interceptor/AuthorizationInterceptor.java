package com.dh.common.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dh.common.annotation.NoLogin;

/**
 * 权限验证
 *
 * @author zhangbi617
 * @date 02/05/2017
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	static Logger LOG = LoggerFactory.getLogger(AuthorizationInterceptor.class);

	@Value("${interceptor.isallow}")
	private boolean isallow;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!isallow) {
			// TODO

			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Class<?> clazz = handlerMethod.getBeanType();
			Method m = handlerMethod.getMethod();
			if (clazz.isAnnotationPresent(NoLogin.class) || m.isAnnotationPresent(NoLogin.class)) {
				return true;
			}

			return true;

		} else {
			return true;
		}
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
}
