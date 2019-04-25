package com.dh.common.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类 基于springboot 2.0之后的，
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements ApplicationListener<WebServerInitializedEvent> {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.dh")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("", "", "");
		return new ApiInfoBuilder().title("").contact(contact).description("").version("").build();
	}

	private String getAddress() {
		String hostAddress;
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostAddress = "127.0.0.1";
		}
		return hostAddress;
	}

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		int serverPort = event.getWebServer().getPort();
		System.err.println("swagger访问地址:http://" + getAddress() + ":" + serverPort + "/swagger-ui.html");

	}

}
