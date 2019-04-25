package com.dh.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DataSourceProperties {

	private HikariConfig mysql;

	private HikariConfig pg;

}