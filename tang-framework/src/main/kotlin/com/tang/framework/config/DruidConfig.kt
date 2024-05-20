package com.tang.framework.config

import com.alibaba.druid.spring.boot3.autoconfigure.properties.DruidStatProperties
import com.alibaba.druid.util.Utils
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Tang
 */
@Configuration
class DruidConfig {

    /**
     * 去除 Druid 监控页面底部的广告
     */
    @Bean
    fun removeDruidAD(properties: DruidStatProperties): FilterRegistrationBean<Filter> {
        val statViewServlet = properties.statViewServlet
        val urlPattern = statViewServlet.urlPattern.ifBlank { "/druid/*" }
        val commonJsPattern = urlPattern.replace("\\*".toRegex(), "js/common.js")
        val filePath = "support/http/resources/js/common.js"
        val text = Utils.readFromResource(filePath)
            .replace("this.buildFooter();", "")

        val registrationBean = FilterRegistrationBean<Filter>()
        registrationBean.filter = Filter { request, response, chain ->
            chain.doFilter(request, response)
            response.resetBuffer()
            response.writer.write(text)
        }
        registrationBean.addUrlPatterns(commonJsPattern)
        return registrationBean
    }

}
