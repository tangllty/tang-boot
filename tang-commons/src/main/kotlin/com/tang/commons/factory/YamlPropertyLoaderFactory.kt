package com.tang.commons.factory

import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory

/**
 * Yaml 配置加载工厂
 *
 * @author Tang
 */
class YamlPropertyLoaderFactory: PropertySourceFactory {

    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        return YamlPropertySourceLoader().load(resource.resource.filename, resource.resource).first()
    }

}
