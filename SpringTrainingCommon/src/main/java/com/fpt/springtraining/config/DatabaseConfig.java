package com.fpt.springtraining.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configure database connection. Replace hibernate xml configuration file 
 * @author ThaiTC
 *
 */
@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:com/fpt/springtraining/config/persistence.properties" })
public class DatabaseConfig {
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.user";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "data.entities";

	private static final String PROPERTY_NAME_HBM2DDL = "hibernate.hbm2ddl.auto";

	@Resource
	private Environment m_env;

	/**
	 * Initialize data source
	 * 
	 * @return
	 */
	@Bean
	public DataSource initDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(
			m_env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER)
		);

		dataSource.setUrl(
			m_env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL)
		);

		dataSource.setUsername(
			m_env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME)
		);

		dataSource.setPassword(
			m_env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD)
		);

		return dataSource;
	}

	/**
	 * Setting Hibernate options
	 * 
	 * @return
	 */
	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put(
			PROPERTY_NAME_HIBERNATE_DIALECT,
			m_env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT)
		);

		properties.put(
			PROPERTY_NAME_HIBERNATE_SHOW_SQL,
			m_env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL)
		);

		properties.put(
			PROPERTY_NAME_HBM2DDL,
			m_env.getRequiredProperty(PROPERTY_NAME_HBM2DDL)
		);

		return properties;
	}

	/**
	 * Return session factory 
	 * 
	 * @return
	 */
	@Bean(name="sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

		localSessionFactoryBean.setDataSource(initDataSource());

		localSessionFactoryBean.setPackagesToScan(
			m_env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN)
		);

		localSessionFactoryBean.setHibernateProperties(hibernateProperties());

		return localSessionFactoryBean;
	}

	/**
	 * Return transaction manager
	 * 
	 * @param sessionFactory
	 * @return
	 */
	@Bean
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
}