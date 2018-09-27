package com.demo.plp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class PlpApplication extends SpringBootServletInitializer {

	public PlpApplication(){
		super();
		setRegisterErrorPageFilter(false);
	}
	
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PlpApplication.class);
		ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PlpApplication.class);
	}

}
