package com.tang.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;

/**
 * 程序注解配置
 *
 * @author Tang
 */
@Indexed
@Configuration
@MapperScan("com.tang.**.mapper")
public class ApplicationConfig {
}
