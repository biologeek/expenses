package io.biologeek.expenses.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories("io.biologeek.expenses.repositories")
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
}
