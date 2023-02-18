package com.tang.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tang.commons.core.autoconfigure.TangProperties;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

/**
 * swagger 配置
 *
 * @author Tang
 */
@Configuration
public class SwaggerConfig {

    @Autowired
    private TangProperties tangProperties;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("权限管理系统")
                .description("description...")
                .version("v" + tangProperties.getVersion())
            .contact(new Contact()
                .name("Tang")
                .email("")
                .url("https://gitee.com/tangllty/tang-vue")));
    }

}
