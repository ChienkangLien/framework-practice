package org.tutorial.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.tutorial")
@Import(JPAConfig.class) // 統一管理第三方bean
@EnableAspectJAutoProxy(proxyTargetClass = true) // proxyTargetClass默認為false，如果目標類實現了介面，或者只對公共方法進行攔截，則保持默認值proxyTargetClass = false就足夠了。只有在需要攔截非公共方法或處理沒有實現介面的類時，才需要將proxyTargetClass設置為true
public class AppConfig {
	// 根級別的配置
    // 配置數據源、事務管理器等等
	
}
