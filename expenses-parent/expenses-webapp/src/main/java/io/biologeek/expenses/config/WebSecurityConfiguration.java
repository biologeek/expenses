package io.biologeek.expenses.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final int SHA_ENCODING_STRENGTH = 256;

	@Autowired
	DataSource ds;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationProvider authenticationProvider;

	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().dataSource(ds)//
				.passwordEncoder(new ShaPasswordEncoder(SHA_ENCODING_STRENGTH))//
				.usersByUsernameQuery("select login,password from registereduser where login = ?")//
				.authoritiesByUsernameQuery("select username, role from registereduser where login = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.exceptionHandling().authenticationEntryPoint(http401());
		http.csrf().disable()//
		.authorizeRequests()//
		.anyRequest().authenticated()//
		;
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
		auth.userDetailsService(userDetailsService);
    }

	private AuthenticationEntryPoint http401() {

		AuthenticationEntryPoint authenticationEntryPoint = new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong username, password or authorization");
			}
			
		};
		return authenticationEntryPoint;
	}

}
