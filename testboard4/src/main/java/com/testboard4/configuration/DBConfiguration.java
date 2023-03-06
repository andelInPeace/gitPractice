package com.testboard4.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//@configuration : 스프링부트 환경설정 클래스임을 명시, 자동으로 빈 등록 
//이 에너테이션이 붙게 되면 @ComponentScan이 스캔할 때 이 클래스에 @Bean 으로 지정한 모든 빈들도 IoC Container 애 등록됨 


@Configuration
@PropertySource("classpath:/application.properties")
public class DBConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	
		// Hikari 설정1 
		// @Bean : return 되는 객체를 IoC 컨테이너에 등록 
		// 특별히 지정하는 이름이 없다면 IoC 컨테이너에 해당 method명으로 등록. 
		// 물론 이름 지정도 가능 하나, 보통 메서드명으로 지정 ekfaus 
		// application.properties 파일로부터 데이터베이스 관련된 정보를 읽어와서 히카리 설정 객체 리턴 
		
		
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	HikariConfig hikariConfig() {
		
		return new HikariConfig();
	}
	
			// Hikari 설정2 
			// 히카리 설정 객체(HikariConfig)를 넘겨받아서 DataSource 객체를 리턴 
			// 만약 아이디나 패스워드가 틀렸다면 이 단계에서 오류 발생. 다시금 application.properties 파일 체크 
			// DB 연결이 잘 되었는지 확인해보기 위해서 콘솔에 dataSource 객체를 toString() 메서드로 출력 
			// 히카리풀 뒤에 숫자가 붙어서 나옴 --> HikariDataSource(HikariPool-1)
			// 이 단계를 통해서 히카리CP(Connection Pool) 결이 완성 
			// 이제 DB 연결이 필요한 부분에서 이 dataSource 를 가지고 연결 해 주면 됨 
	
	@Bean
	DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig() );
		System.out.println(dataSource.toString() );
		
		return dataSource;
	}
	
		//MyBatis 설정1 : 
		// SqlSessionFactory 생성을 위해서 내부의  sqlSessionFactoryBean 을 사용 
		// 이 때, 데이터소스 객체를 넘겨 받아서 처리 되고, 아니면 setDataSource(dataSource()) 해줘도 됨. 
		// 기본적인 설정 3가지 
		// 	setDataSource     : 빌드된 dataSource 셋팅 
		// 	setMapperLocatios : SQL 구문이 작성된 Mapper.xml 의 경로를 정확히 입력 
		//  setTypeAliasesPackage : 인자로 Alias 대상 클래스가 위치한 패키 경로 
				
		// 주의사항 
		// : SqlSessionFactory 에 저장할 config 설정 시 Mapper 에서 사용하고자 하는 DTO, VO, Entity 에 대해서 setTypeAliasesPackage 지정 필요 
		// : 만약 지정해줒 않는다면 aliases 를 찾지 못한다는 오류가 발생할 수 있음 
	
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/MemberMapper.xml") );
		// classpath:/mapper/**/*Mapper.xml : mapper 하위 폴더에서 ~~mapper 파일 포함 하게끔 저장도 가능 
		// mapper 에 대한 리소스는 어디에서 가져오는가 
		// - ApplicatioContext 객체에서 가져올 수 있음 
		// - ApplicationContext 는 쉽게 말해 프레임워크 컨테이너라고 생각하면 됨 
		// - Applicationcontext 는 애플리케이션이 스타트해서 끝나는 그 순간까지 이 애플리케이션에서 필요한 모든 자원들을 모아놓고 관리 
		factoryBean.setTypeAliasesPackage("com.testboard4.dto");
		
		return factoryBean.getObject();
	}
	
		//MyBatis 설정2 : SqlSessionTemplate 객체 생성 <--SqlSessionFactory 
		// 넘겨 받은 sqlSessionFactory를 통해서 sqlSessionTemplate 객체를 생성 및 리턴// \
		// SQL 구문의 실행과 트랜잭션 등을 관리하는 가장 열일하는 애 
		// MyBatis 의 sqlSession 객체가 Spring+MyBatis 연동 모듈에서는 sqlSessionTemplate 이 대체 
	
	@Bean
	SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
