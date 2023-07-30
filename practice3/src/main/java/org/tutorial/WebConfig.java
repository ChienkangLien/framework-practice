package org.tutorial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	// Web層相關的配置
    // 配置視圖解析器、攔截器等等
	
	
	@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
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
//	
	@Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
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
