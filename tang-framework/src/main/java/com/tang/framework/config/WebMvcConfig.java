package com.tang.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tang.commons.autoconfigure.FileProperties;
import com.tang.commons.constants.UploadsPrefix;
import com.tang.framework.interceptor.DemoModeInterceptor;

/**
 * WebMvc 配置
 *
 * @author Tang
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final FileProperties fileProperties;

    public WebMvcConfig(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(UploadsPrefix.UPLOADS + "/**").addResourceLocations("file:" + fileProperties.getUploads() + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DemoModeInterceptor()).addPathPatterns("/**");
    }

}
