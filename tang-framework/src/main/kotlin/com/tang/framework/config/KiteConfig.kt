package com.tang.framework.config

import com.tang.kite.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan(["com.tang.**.mapper"])
class KiteConfig
