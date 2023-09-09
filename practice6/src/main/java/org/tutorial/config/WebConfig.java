package org.tutorial.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tutorial.interceptor.ProjectInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	ProjectInterceptor interceptor;

	// 注意excludePathPatterns路徑的寫法
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(interceptor).addPathPatterns("/emps/*").excludePathPatterns("/emps/dept/**");
		registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/swagger-ui.html","/webjars/**","/","/csrf");
	}

}
