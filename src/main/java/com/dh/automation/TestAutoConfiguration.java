package com.dh.automation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(AutomationService.class)
public class TestAutoConfiguration {

	@Bean
	AutomationService helloService() {
		AutomationService helloService = new AutomationService();

		return helloService;
	}
}
