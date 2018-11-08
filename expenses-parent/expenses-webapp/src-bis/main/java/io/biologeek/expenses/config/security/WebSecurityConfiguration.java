package io.biologeek.expenses.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
// @ComponentScan("io.biologeek.expenses.config.security")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final int SHA_ENCODING_STRENGTH = 256;

	@Autowired
	DataSource ds;

	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery("select login, password, isactive from \"user\" where login = ?")//
				.authoritiesByUsernameQuery(
						"select u.login, r.name from \"user\" u inner join role_user ru on ru.user_id = u.id inner join roles r on r.id = ru.role_id where u.login = ?")//
				.dataSource(ds)//
				.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()//
				.antMatchers("/").permitAll()//
				.antMatchers("/user/login").permitAll()//
				.antMatchers("/user/register").permitAll()//
				.antMatchers("/admin/**").hasAuthority("ADMIN")//
				.anyRequest().authenticated()//
				.and()
			    .formLogin().loginPage("/authentication/login")
			    .usernameParameter("login").passwordParameter("password")
			    .and()
			    .logout().logoutSuccessUrl("/user/logout") 
				//.and().httpBasic().authenticationEntryPoint(authEntryPoint)
				.and().csrf().disable()//
				.exceptionHandling().authenticationEntryPoint(
						new org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint(
								"headerValue"));
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider pro =  new DaoAuthenticationProvider();
		pro.setUserDetailsService(userDetailsService);
		return pro;
	}

	/**
	 * SHA-256 encryption for passwords
	 * 
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Component
	public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
				throws IOException, ServletException {
			response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			PrintWriter writer = response.getWriter();
			writer.println("HTTP Status 401 - " + authEx.getMessage());
		}

		@Override
	    public void afterPropertiesSet() throws Exception {
	        setRealmName("Expenses");
	        super.afterPropertiesSet();
	    }
	}
}
