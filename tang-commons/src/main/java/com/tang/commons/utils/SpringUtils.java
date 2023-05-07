package com.tang.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类 在非 Spring 环境中获取 Bean
 *
 * @author Tang
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    private static void setStaticBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        SpringUtils.beanFactory = beanFactory;
    }

    private static void setStaticAppCtx(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.setStaticBeanFactory(beanFactory);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        SpringUtils.setStaticAppCtx(applicationContext);
    }

    public static ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
