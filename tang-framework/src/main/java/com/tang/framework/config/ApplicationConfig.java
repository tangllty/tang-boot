package com.tang.framework.config;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Indexed;

/**
 * 程序注解配置
 *
 * @author Tang
 */
@EnableAdminServer
@EnableAsync
@Indexed
@Configuration
@MapperScan("com.tang.**.mapper")
public class ApplicationConfig {
}
