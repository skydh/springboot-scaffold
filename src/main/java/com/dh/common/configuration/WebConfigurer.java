package com.dh.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.dh.common.constants.WebConstants;
import com.dh.common.interceptor.AuthorizationInterceptor;
import com.dh.common.interceptor.IllegalResquestParamInterceptor;

@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

	@Autowired
	private AuthorizationInterceptor authorizationInterceptor;
	@Autowired
	private IllegalResquestParamInterceptor illegalResquestParamInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 * 拦截所有请求
		 */
		registry.addInterceptor(illegalResquestParamInterceptor).addPathPatterns(WebConstants.WEB_PREFIX + "/**");
		registry.addInterceptor(authorizationInterceptor).addPathPatterns(WebConstants.WEB_PREFIX + "/**");
		super.addInterceptors(registry);
	}

	/**
	 * 静态资源
	 *
	 * @param registry
	 *            注册类
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// swagger
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

	}

}