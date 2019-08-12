package com.dh.common.configuration;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "spring.jpa")
public class HibernateProperties {

	private JpaProperties mysql;
	
	private JpaProperties pgsql;

}
