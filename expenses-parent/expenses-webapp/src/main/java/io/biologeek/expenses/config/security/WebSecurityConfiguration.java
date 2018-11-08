package io.biologeek.expenses.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import io.biologeek.expenses.domain.beans.security.OwnPasswordEncoder;

@Configuration
@EnableWebSecurity
//@ComponentScan("io.biologeek.expenses.config.security")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	public static final int SHA_ENCODING_STRENGTH = 256;

	@Autowired
	DataSource ds;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationProvider authenticationProvider;

	    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
}

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http.
	                authorizeRequests()
	                .antMatchers("/").permitAll()
	                .antMatchers("/login").permitAll()
	                .antMatchers("/registration").permitAll()
	                .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
	                .authenticated().and().csrf().disable().formLogin()
	                .loginPage("/login").failureUrl("/login?error=true")
	                .defaultSuccessUrl("/admin/home")
	                .usernameParameter("email")
	                .passwordParameter("password")
	                .and().logout()
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                .logoutSuccessUrl("/").and().exceptionHandling()
	                .accessDeniedPage("/access-denied");
	    }

	    @Override
	    public void configure(WebSecurity web) throws Exception {
	        web
	                .ignoring()
	                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	/**
	 * SHA-256 encryption for passwords
	 * 
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new OwnPasswordEncoder();
	}

}
