package com.demo.plp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.plp.interceptor.LoginInterceptor;
import com.demo.plp.interceptor.UserInterceptor;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private UserInterceptor userInterceptor;
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry interceptorregistry) {
		interceptorregistry.addInterceptor(userInterceptor).addPathPatterns("/**")
			.excludePathPatterns("/user/**","/index.html",
					"/files/**","/static/files/**");
		interceptorregistry.addInterceptor(loginInterceptor).addPathPatterns("/index.html");
	}
	
}
