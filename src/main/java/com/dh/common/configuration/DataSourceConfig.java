package com.dh.common.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	@Autowired
	private DataSourceProperties dataSourceProperties;

	@Bean(name = "pgDataSource")
	@Qualifier("pgDataSource")
	public DataSource pgDataSource() {
		HikariConfig hikariConfig = dataSourceProperties.getPg();
		return new HikariDataSource(hikariConfig);
	}

	@Bean(name = "mysqlDataSource")
	@Qualifier("mysqlDataSource")
	@Primary
	public HikariDataSource mysqlDataSource() {
		HikariConfig hikariConfig = dataSourceProperties.getMysql();
		return new HikariDataSource(hikariConfig);
	}

	@Bean(name = "pgJdbcTemplate")
	public JdbcTemplate pgJdbcTemplate(@Qualifier("pgDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "mysqlJdbcTemplate")
	public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}