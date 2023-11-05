package org.tutorial;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Practice7Application.class);
	}

}

// 如果計劃將應用程序嵌入到Spring Boot的內置容器中並以獨立的方式運行，則不需要ServletInitializer
// 但如果打算部署到外部的Servlet容器中，則需要創建並配置SpringBootServletInitializer類
