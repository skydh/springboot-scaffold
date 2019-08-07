package com.dh.common.configuration;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryPg", transactionManagerRef = "transactionManagerPg", basePackages = {
		"com.dh.pgsql" }) // 设置Repository所在位置
public class PgsqlConfig {

	@Autowired
	@Qualifier("pgDataSource")
	private DataSource pgDataSource;

	@Bean(name = "entityManagerPg")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactorySecondary(builder).getObject().createEntityManager();
	}

	@Bean(name = "entityManagerFactoryPg")
	public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(pgDataSource).properties(getVendorProperties())
				.packages("com.dh.pgsql") // 设置实体类所在位置
				.persistenceUnit("pgPersistenceUnit").build();
	}

	@Autowired
	private JpaProperties jpaProperties;

	private Map<String, String> getVendorProperties() {
		return jpaProperties.getProperties();
	}

	@Bean(name = "transactionManagerPg")
	PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
	}

}