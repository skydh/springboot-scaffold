package com.dh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动
 * 
 * @author Lenovo
 *
 */
@SpringBootApplication(scanBasePackages = { "com.dh" })
@EnableRedisHttpSession
@EnableTransactionManagement
public class WebApplication {
	public static void main(String args[]) {
		System.setProperty("mail.mime.splitlongparameters", "false");
		SpringApplication.run(WebApplication.class, args);

	}

}