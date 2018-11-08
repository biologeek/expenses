package io.biologeek.expenses.config;

import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.biologeek.expenses.config.security.SimpleTokenAuthenticationFilter;

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
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	
//	@Bean
//	public EmbeddedServletContainerFactory servletContainerFactory() {
//	    return new TomcatEmbeddedServletContainerFactory() {
//
//	        @Override
//	        protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
//	                Tomcat tomcat) {
//	            try {
//	                Context context = tomcat.addWebapp("/expenses-mobile", "lib/expenses-mobile-webui-0.0.1-SNAPSHOT.war");
//	                WebappLoader loader =
//	                    new WebappLoader(Thread.currentThread().getContextClassLoader());
//	                context.setLoader(loader);
//	            } catch (ServletException ex) {
//	                throw new IllegalStateException("Failed to add webapp", ex);
//	            }
//	            return super.getTomcatEmbeddedServletContainer(tomcat);
//	        }
//
//	    };
//	}
	
	@Bean
	public EmbeddedServletContainerCustomizer customize(){
		return new EmbeddedServletContainerCustomizer() {
			
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.setPort(8090);
				container.setContextPath("/expenses");
			}
		};
	}


	@Bean
	public FilterRegistrationBean loginFilterRegistration() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(loginFilter());
	    registration.addUrlPatterns("/expenses/*");
	    registration.setName("loginFilter");
	    registration.setOrder(1);
	    return registration;
	}

	@Bean
	public Filter loginFilter() {
		return new SimpleTokenAuthenticationFilter();
	}
}
