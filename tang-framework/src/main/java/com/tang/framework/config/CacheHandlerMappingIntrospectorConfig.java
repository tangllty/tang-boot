package com.tang.framework.config;

import java.util.EnumSet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;

/**
 * 缓存 HandlerMappingIntrospector
 *
 * @author Tang
 */
@Configuration
public class CacheHandlerMappingIntrospectorConfig {

    @Bean
    static FilterRegistrationBean<Filter> handlerMappingIntrospectorCacheFilter(HandlerMappingIntrospector handlerMappingIntrospector) {
        var cacheFilter = handlerMappingIntrospector.createCacheFilter();
        var registrationBean = new FilterRegistrationBean<>(cacheFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registrationBean;
    }

}
