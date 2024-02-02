package com.tang.commons.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

import com.tang.commons.enumeration.SizeUnit
import com.tang.commons.factory.YamlPropertyLoaderFactory

/**
 * 文件配置属性
 *
 * @author Tang
 */
@Configuration
@ConfigurationProperties(FileProperties.FILE_PREFIX)
@PropertySource(value = ["classpath:application-file.yml"], factory = YamlPropertyLoaderFactory::class)
class FileProperties {

    companion object {
        const val FILE_PREFIX = "file"
    }

    /**
     * 文件上传路径
     */
    var uploads: String = ""

    /**
     * 文件大小限制
     */
    var maxSize: Double = 0.0

    /**
     * 文件大小单位
     */
    var sizeUnit: SizeUnit = SizeUnit.MB

    /**
     * 文件名最大长度
     */
    var maxFileNameLength: Int = 0

    /**
     * 文件类型限制
     */
    var allowedTypes: Array<String> = arrayOf()

}
