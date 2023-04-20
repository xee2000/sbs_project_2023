package com.ljh.exam.jsp_bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ljh.exam.jsp_bootstrap.interceptor.BeforeActionInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

		
}
