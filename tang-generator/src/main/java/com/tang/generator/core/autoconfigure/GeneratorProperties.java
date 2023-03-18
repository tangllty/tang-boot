package com.tang.generator.core.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 代码生成配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(GeneratorProperties.GENERATOR_PREFIX)
@PropertySource("classpath:generator.yml")
public class GeneratorProperties {

    public static final String GENERATOR_PREFIX = "generator";

    @Value("${author}")
    private String author;

    @Value("${package-name}")
    private String packageName;

    @Value("${remove-pre}")
    private String removePre;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRemovePre() {
        return removePre;
    }

    public void setRemovePre(String removePre) {
        this.removePre = removePre;
    }

}
