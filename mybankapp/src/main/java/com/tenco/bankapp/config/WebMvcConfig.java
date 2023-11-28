package com.tenco.bankapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bankapp.handler.AuthInterceptor;

// 스프링 부트 설정 클래스라는 의미
@Configuration // Ioc 등록 -> 2개이상 Ioc 등록 처리
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private AuthInterceptor authInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/account/**") // 패턴 추가
				.addPathPatterns("/auth/**");

	}
}
