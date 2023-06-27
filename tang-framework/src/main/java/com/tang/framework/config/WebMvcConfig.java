package com.tang.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tang.commons.autoconfigure.TangProperties;
import com.tang.commons.constants.UploadsPrefix;

/**
 * WebMvc 配置
 *
 * @author Tang
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TangProperties tangProperties;

    public WebMvcConfig(TangProperties tangProperties) {
        this.tangProperties = tangProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(UploadsPrefix.UPLOADS + "/**").addResourceLocations("file:" + tangProperties.getUploads() + "/");
    }

}
