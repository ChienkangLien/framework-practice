package org.tutorial.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {

	// 以下情境瀏覽器會發送預檢請求
	// 用非簡單請求方法（Non-Simple Request Methods）：
	// 當你使用非簡單的HTTP請求方法，例如PUT、DELETE、CONNECT、OPTIONS、TRACE和PATCH，並且帶有特定額外標頭時，瀏覽器會自動發送預檢請求。
	// 使用特定標頭（Non-Simple Request Headers）：
	// 當你在請求中使用了特定的標頭，例如Authorization、Content-Type（值不是以下之一：application/x-www-form-urlencoded、multipart/form-data、text/plain）、自訂的標頭等

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();

		// 允許的來源，不要使用通配符 "*"，而是明確指定允許的來源
		config.addAllowedOrigin("http://127.0.0.1:5500"); // 替換成你的前端應用域名

		// 允許攜帶認證資訊
		config.setAllowCredentials(true);

		// 允許的 HTTP 方法
		config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));

		// 允許的標頭
		config.addAllowedHeader("*");

		// 選擇性暴露的標頭
		config.addExposedHeader("Content-Type"); // 或者你想暴露的其他標頭
		config.addExposedHeader("custom-header");

		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);

		return new CorsFilter(configSource);
	}

}
