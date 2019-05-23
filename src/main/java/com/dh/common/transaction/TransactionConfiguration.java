
package com.dh.common.transaction;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionConfiguration {

	// 创建pgsql事务管理器1
	@Bean(name = "txManagerPg")
	public PlatformTransactionManager txManager(@Qualifier("pgDataSource") DataSource dataSource) {

		return new DataSourceTransactionManager(dataSource);
	}

	// 创建mysql事务管理器2
	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager txManager2(EntityManagerFactory factory) {

		return new JpaTransactionManager(factory);
	}

}
