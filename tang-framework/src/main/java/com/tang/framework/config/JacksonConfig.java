package com.tang.framework.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tang.commons.autoconfigure.tang.DateFormatProperties;
import com.tang.framework.config.serializer.LongToStringSerializer;

/**
 * Jackson 配置类
 *
 * @author Tang
 */
@Configuration
public class JacksonConfig {

    private final DateFormatProperties dateFormatProperties;

    public JacksonConfig(DateFormatProperties dateFormatProperties) {
        this.dateFormatProperties = dateFormatProperties;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        final var localDateFormatter = DateTimeFormatter.ofPattern(dateFormatProperties.getLocalDate());
        final var localDateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatProperties.getLocalDateTime());
        return builder -> builder
            .serializerByType(Long.TYPE, LongToStringSerializer.INSTANCE)
            .serializerByType(Long.class, LongToStringSerializer.INSTANCE)
            .serializerByType(LocalDate.class, new LocalDateSerializer(localDateFormatter))
            .deserializerByType(LocalDate.class, new LocalDateDeserializer(localDateFormatter))
            .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormatter))
            .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormatter));
    }

}
