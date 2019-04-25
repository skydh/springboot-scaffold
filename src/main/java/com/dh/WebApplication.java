package com.dh;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动
 * 
 * @author Lenovo
 *
 */
@SpringBootApplication(scanBasePackages = { "com.dh" })

@EnableTransactionManagement
public class WebApplication {

	// 创建pgsql事务管理器1
	@Bean(name = "txManagerPg")
	public PlatformTransactionManager txManager(@Qualifier("pgDataSource") DataSource dataSource) {

		return new DataSourceTransactionManager(dataSource);
	}

	// 创建mysql事务管理器2
	@Primary
	@Bean(name = "transactionManag")
	public PlatformTransactionManager txManager2(EntityManagerFactory factory) {

		return new JpaTransactionManager(factory);
	}

	public static void main(String args[]) {
		SpringApplication.run(WebApplication.class, args);

	}

}