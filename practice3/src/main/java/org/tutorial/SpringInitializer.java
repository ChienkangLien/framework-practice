package org.tutorial;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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
}
