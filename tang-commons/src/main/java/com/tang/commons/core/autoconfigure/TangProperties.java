package com.tang.commons.core.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 项目配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(TangProperties.TANG_PREFIX)
public class TangProperties {

    public static final String TANG_PREFIX = "tang";

    private String name;

    private String version;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
