package com.tang.framework.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tang.framework.interceptor.AutoFillInterceptor;
import com.tang.framework.interceptor.DictPermissionInterceptor;

/**
 * MyBatis配置
 *
 * @author Tang
 */
@Configuration
public class MyBatisConfig {

    /**
     * 注册自定义 MyBatis 配置
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.addInterceptor(new AutoFillInterceptor());
            configuration.addInterceptor(new DictPermissionInterceptor());
        };
    }

}
