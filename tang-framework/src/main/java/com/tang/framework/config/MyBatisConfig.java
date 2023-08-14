package com.tang.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tang.framework.interceptor.AutoFillInterceptor;

/**
 * MyBatis配置
 *
 * @author Tang
 */
@Configuration
public class MyBatisConfig {

    @Bean
    public AutoFillInterceptor autoFillInterceptor() {
        return new AutoFillInterceptor();
    }

}
