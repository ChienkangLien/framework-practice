package org.tutorial;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.tutorial.config.AppConfig;
import org.tutorial.config.WebConfig;

public class SpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	// JavaConfig方式、取代.xml註冊
	
	// web配置
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	// 根配置
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { AppConfig.class };
	}

	// 所有請求
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	// 取代.xml註冊
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		return new Filter[] {filter};
	}
}
