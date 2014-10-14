/**
 * Copyright © 2014 Remi Guillemette <rguillemette@n4dev.ca>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package ca.n4dev.dev.worktime.config;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author rguillemette
 * @since Oct 13, 2014
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ca.n4dev.dev.worktime.repository")
public class SpringHibernateJPAConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		try {
			// Obtain our environment naming context
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			// Look up our data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/WorktimeDB");

//			if (ds == null) {
//
//				ds = new DataSource();
//				PoolProperties p = new PoolProperties();
//				
//				p.setUrl(env.getRequiredProperty("")); // "jdbc:mysql://localhost:3306/mysql"
//				p.setDriverClassName(env.getRequiredProperty("")); // "com.mysql.jdbc.Driver"
//				p.setUsername(env.getRequiredProperty("")); // "root"
//				p.setPassword(env.getRequiredProperty("")); // "password"
//				p.setJmxEnabled(Boolean.valueOf(env.getRequiredProperty(""))); // false
//				p.setTestWhileIdle(Boolean.valueOf(env.getRequiredProperty(""))); // false
//				p.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty(""))); // true
//				p.setValidationQuery(env.getRequiredProperty("")); // "SELECT 1"
//				p.setTestOnReturn(Boolean.valueOf(env.getRequiredProperty(""))); // false
//				p.setValidationInterval(Integer.parseInt(env.getRequiredProperty(""))); // 30000
//				p.setTimeBetweenEvictionRunsMillis(Integer.parseInt(env.getRequiredProperty(""))); // 30000
//				p.setMaxActive(Integer.parseInt(env.getRequiredProperty(""))); // 100
//				p.setInitialSize(Integer.parseInt(env.getRequiredProperty(""))); // 10
//				p.setMaxWait(Integer.parseInt(env.getRequiredProperty(""))); // 10000
//				p.setRemoveAbandonedTimeout(Integer.parseInt(env.getRequiredProperty(""))); // 60
//				p.setMinEvictableIdleTimeMillis(Integer.parseInt(env.getRequiredProperty(""))); // 30000
//				p.setMinIdle(Integer.parseInt(env.getRequiredProperty(""))); // 10
//				p.setLogAbandoned(Boolean.valueOf(env.getRequiredProperty(""))); // true
//				p.setRemoveAbandoned(Boolean.valueOf(env.getRequiredProperty(""))); // true
//				p.setJdbcInterceptors(env.getRequiredProperty("")); // "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer"
//
//				
//				ds.setPoolProperties(p);
//				
//			}

			return ds;

		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to get or create the datasource!");
		}
	}

	// Declare a JPA entityManagerFactory
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceXmlLocation("classpath*:META-INF/persistence.xml");
		em.setPersistenceUnitName("hibernatePersistenceUnit");
		em.setDataSource(dataSource());
		HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
		vendor.setShowSql(false);
		em.setJpaVendorAdapter(vendor);
		return em;
	}

	// Declare a transaction manager
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
