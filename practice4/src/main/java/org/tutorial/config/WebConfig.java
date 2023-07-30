package org.tutorial.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.tutorial.interceptor.ProjectInterceptor;

@ComponentScan(basePackages = "org.tutorial")
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	// Web層相關的配置
    // 配置視圖解析器、攔截器等等
	
	@Autowired
	ProjectInterceptor projectInterceptor;
	
//	@Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.jsp("/WEB-INF/views/", ".jsp");
//    }
	
	// 取代以上jsp配置
	@Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        return resolver;
    }
	
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver);
		return engine;
	}
	
	@Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
	
	// 請求以 /resources/ 開頭的 URL 時，Spring MVC 將在 /resources/ 目錄下查找對應的資源文件，
	// 並返回給客戶端。這樣，可以提供靜態資源（如 CSS、JavaScript、圖片等）給前端。
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**")
	            .addResourceLocations("/resources/");
	}

	// 被上述addResourceHandlers取代
	// 無法找到對應的處理器來處理請求的話，就會交給 DefaultServletHttpRequestHandler，
	// 會將請求轉給 Servlet 容器的預設 Servlet（也就是容器本身提供靜態資源的 Servlet）
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}
	
	// 默認的內容類型為 application/json，
	// 針對表單提交添加了對 application/x-www-form-urlencoded 內容類型的支援
	// 如果請求上有錯誤，可以添加
//	@Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.defaultContentType(MediaType.APPLICATION_JSON)
//                   .mediaType("json", MediaType.APPLICATION_JSON)
//                   .mediaType("form", MediaType.APPLICATION_FORM_URLENCODED);
//    }
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
    }
	
	// 添加攔截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(projectInterceptor).addPathPatterns("/**").excludePathPatterns("/resources/**");
	}
}

// Converter for String to LocalDate
class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(source, formatter);
    }
    
    
}
