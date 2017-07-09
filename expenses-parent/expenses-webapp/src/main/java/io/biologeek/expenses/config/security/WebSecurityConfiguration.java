package io.biologeek.expenses.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
//@EnableWebSecurity
//@ComponentScan("io.biologeek.expenses.config.security")
public class WebSecurityConfiguration { // extends WebSecurityConfigurerAdapter {

	public static final int SHA_ENCODING_STRENGTH = 256;

	@Autowired
	DataSource ds;
	/*
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationProvider authenticationProvider;

	@Autowired
	private PersistentTokenRepository tokenRepository;
	
	// public void configureGlobal(AuthenticationManagerBuilder builder) throws
	// Exception {
	// builder.jdbcAuthentication().dataSource(ds)//
	// .passwordEncoder(new ShaPasswordEncoder(SHA_ENCODING_STRENGTH))//
	// .usersByUsernameQuery("select login,password from user where login =
	// ?")//
	// .authoritiesByUsernameQuery("select username, role from user where login
	// = ?");
	// }

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// http.exceptionHandling().authenticationEntryPoint(http401());
		http.csrf().disable().authorizeRequests()//
				.anyRequest().permitAll();
		// .anyRequest().authenticated()//
		// .regexMatchers("/login", "/error", "/logout").permitAll()//
		// .and().formLogin().loginPage("/login").loginProcessingUrl("/login-process")//
		// .usernameParameter("username").passwordParameter("passord")//
		// .and().rememberMe().rememberMeParameter("remember-me")//
		// .tokenRepository(tokenRepository).tokenValiditySeconds(86400)//
		// .and().logout().permitAll()//
		// .and().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(ds)//
				.passwordEncoder(new ShaPasswordEncoder(SHA_ENCODING_STRENGTH))//
				.usersByUsernameQuery("select login,password from public.user where login = ?")//
				.authoritiesByUsernameQuery("select username, role from public.user where login = ?")//
		;
		//// .inMemoryAuthentication().withUser("xcaron").password("32653265").roles("USER");
		// auth.authenticationProvider(authenticationProvider);
		// auth.userDetailsService(userDetailsService);
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

	@Bean
	public PersistentTokenRepository getPersistentTokenRepository() {
		JdbcTokenRepositoryImpl result = new JdbcTokenRepositoryImpl();
		result.setDataSource(ds);
		return result;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}
*/
	/**
	 * SHA-256 encryption for passwords
	 * 
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

}
