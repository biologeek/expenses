package io.biologeek.expenses.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@PropertySources({ @PropertySource("${app.parameters}/application.properties") })
@ComponentScan(basePackages = "io.biologeek")
public class ApplicationConfig {

	@Value("${hibernate.dialect}")
	String dialect;

	@Value("${jdbc.connection.driver}")
	String connectionDriverClassName;

	@Value("${jdbc.connection.url}")
	String connectionURL;

	@Value("${jdbc.connection.user}")
	String connectionUserName;

	@Value("${jdbc.connection.password}")
	String connectionPassword;

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(connectionDriverClassName);
		dataSource.setUrl(connectionURL);
		dataSource.setUsername(connectionUserName);
		dataSource.setPassword(connectionPassword);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("io.biologeek.expenses.domain.beans");
		emf.setJpaProperties(jpaProperties());
		return emf;
	}

	private Properties jpaProperties() {
		Properties props = new Properties();
		props.put("hibernate.dialect", dialect);
		props.put("hibernate.show_sql", true);
		props.put("hibernate.hbm2ddl.auto", "update");
		return props;
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer customize(){
		return new EmbeddedServletContainerCustomizer() {
			
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.setPort(8080);
				container.setContextPath("/expenses");
			}
		};
	}

}
