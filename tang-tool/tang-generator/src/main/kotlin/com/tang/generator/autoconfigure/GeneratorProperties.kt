package com.tang.generator.autoconfigure

import com.tang.commons.factory.YamlPropertyLoaderFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * 代码生成配置
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(GeneratorProperties.GENERATOR_PREFIX)
@PropertySource(value = ["classpath:generator.yml"], factory = YamlPropertyLoaderFactory::class)
class GeneratorProperties {

    companion object {
        const val GENERATOR_PREFIX = "generator"
    }

    var author: String = "Tang"

    var packageName: String = "com.tang.system"

    var removePre: String = "t_,tb_"

}
