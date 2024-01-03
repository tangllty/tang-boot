package com.tang.generator.utils;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * VelocityEngine 工厂
 *
 * @author Tang
 */
public class VelocityInitializer {

    private VelocityInitializer() {
    }

    /**
     * 初始化vm方法
     */
    public static void initVelocity() {
        // 加载classpath目录下的vm文件
        Velocity.setProperty("resource.loader.file.class", ClasspathResourceLoader.class.getName());
        // 定义字符集
        Velocity.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        // 初始化Velocity引擎，指定配置Properties
        Velocity.init();
    }

}
