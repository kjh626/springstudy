package com.gdu.kim08.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@MapperScan(basePackages= {"com.gdu.app08.mapper"})
@PropertySource(value= {"classpath:application.properties"})
@EnableTransactionManagement
@Configuration
public class DBConfig {

	@Autowired
	private Environment env;
	
	// HikaryConfig Bean
	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
		hikariConfig.setJdbcUrl("spring.datasource.hikari.jdbc-url");
		hikariConfig.setUsername("spring.datasource.hikari.username");
		hikariConfig.setPassword("spring.datasource.hikari.password");
		return hikariConfig;
	}
	
	// HikariDataSource Bean
	@Bean(destroyMethod="close")
	public HikariDataSource hikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}
	
	// SqlSessionFactory Bean
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(hikariDataSource());
		bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(env.getProperty("mybatis.config-location")));
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
		return bean.getObject();
	}
	
	
	// SqlSessionTemplate Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	// TransactionManager Bean
	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(hikariDataSource());
	}
	
	
	
}
