package org.tutorial.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // 啟用@Secured管理，另jsr250Enabled管理@RolesAllowed、prePostEnabled管理@PostAuthorize、proxyTargetClass管理代理對象
public class SecurityConfig {

	@Autowired
	private DataSource dataSource;

	// 登入登出行為配置，注銷後會將用戶重定向到登錄頁面是Spring Security的標準行為
	// role與authority是不同的級別
	// 默認開啟csrf(跨站請求偽造)防護
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers("/css/login.css").permitAll() // 放行
				.antMatchers("/**").hasAnyRole("USER", "ADMIN") // 宣告路徑及腳色、也可使用authority
				.anyRequest().authenticated()) // 所有其他請求需要用戶進行身份驗證（即登錄）。如果用戶沒有登錄，他們將被重定向到登錄頁面
				.formLogin((form) -> form.loginPage("/login").permitAll()) // 登入動作
				.logout(logout -> logout.permitAll()) // 登出動作
				.rememberMe() // 啟用Remember-Me功能
				.tokenRepository(persistentTokenRepository()) // token存進資料庫
				.key("my-remember-me-key") // 配置Remember-Me的加密密鑰(可選)
				.tokenValiditySeconds(360); // 配置Remember-Me令牌的有效期時間（以秒為單位）
		;
		return http.build();
	}

	// 資料庫格式需符合spring security規範
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		// 啟動的時候自動創建表
//      tokenRepository.setCreateTableOnStartup(true);
		return tokenRepository;
	}

	// hardcode
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user = User.withUsername("user1").password("user1").roles("USER").build();
//		UserDetails admin = User.withUsername("admin1").password("admin1").roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(user, admin);
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 加鹽加密
//		return NoOpPasswordEncoder.getInstance(); // 明文密碼
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		var provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
