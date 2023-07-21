package com.tang.framework.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tang.framework.config.serializer.LongToStringSerializer;

/**
 * Jackson 配置类
 *
 * @author Tang
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
            .serializerByType(Long.TYPE, LongToStringSerializer.INSTANCE)
            .serializerByType(Long.class, LongToStringSerializer.INSTANCE);
    }

}
