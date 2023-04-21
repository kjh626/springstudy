package com.gdu.app07.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
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

//@PropertySource: 프로퍼티 파일좀 읽어와라(value의 반환타입은 []배열이라 중괄호로 적음, 파일이름만 적은 이유는 resources폴더 밑에 바로 있어서)
@PropertySource(value={"classpath:application.properties"})  // applcation.properties 파일의 속성을 읽어 오자!   -> Environment 랑 세트라고 생각.
@EnableTransactionManagement  // 트랜잭션 처리를 허용한다. (DBConfig에서 앞으로도 계속 사용할 애너테이션)
@Configuration
public class DBConfig {

	// 변수로 따로따로 읽어오는 방법 : 변수 6개 만들어서 6개로 읽어와야 해서 별로?
	// 우리는 @Autowired private Environment env 쓸거임
	@Autowired
	private Environment env; 
	
	// HikariDataSource Bean을 만들기 위한 준비물
	// HikariConfig Bean - 환경 설정을 먼저 만들어줘야 한다. application.properties에 적혀있는 것을 읽는다(가져온다). (properties 파일로 나눠준 이유는 gitignore로 설정해서 올리지 않으려고)
	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
		hikariConfig.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
		hikariConfig.setUsername(env.getProperty("spring.datasource.hikari.username"));
		hikariConfig.setPassword(env.getProperty("spring.datasource.hikari.password"));
		return hikariConfig;
	}
	
	// HikariDataSource Bean (자동완성의 두번째 생성자로 히카리컨피그 넣는 거 있다)
	// destroyMethod="close" : bean 객체의 스코프가 끝날을 경우(스프링에서는 어플리케이션 컨텍스트가 종료되었을 경우로 생각하면 되겠다.) class 속성에 선언한 클래스의 close 메서드를 호출하는 의미이다.
	@Bean(destroyMethod="close")
	public HikariDataSource hikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}
	// 여기까지가 커넥션풀 코드. => 이제 커넥션풀은 HikariDataSource가 담당
	
	
	// SqlSessionFactory Bean
	//          빈의 타입↓   이고 빈의 이름↓
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception { // 쿼리문 처리하는 애라서 try-catch 필요해서 예외 던져버림
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(hikariDataSource());
		bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(env.getProperty("mybatis.config-location")));  // 프로퍼티에 있는 그 정보
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
		return bean.getObject();   // 팩토리빈에서 오브젝트 꺼내면 팩토리 나온다.(설명 나와있음)
	}
	
	// SqlSessionTemplate Bean (기존의 SqlSession)
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {  // 팩토리 부를 건데 팩토리가 예외처리 필요하니까 예외 던지기
		return new SqlSessionTemplate(sqlSessionFactory());  // 자동완성보면 팩토리 받는 생성자 있다.
	}
	
	// ※ Bean 만든 거 계속 다음 Bean의 재료로 사용되고 있다. 아래로 쭉쭉 결론적으로 SqlSessionTemplate Bean 만들려고 하는 것이다. 마이바티스는 얘만 있으면 다 할 수 있으니까
	
	// TransactionManager Bean - 트랜잭션 매니저는 datasource만 있으면 된다.
	// 트랜잭션 하기로 했으면 위에 적어주기로 한 거 있다. @EnableTransactionManagement
	@Bean
	public TransactionManager transactionManager() {
		return new DataSourceTransactionManager(hikariDataSource());
	}
	
	
	
}
