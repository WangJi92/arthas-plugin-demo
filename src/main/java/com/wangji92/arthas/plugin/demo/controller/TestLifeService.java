package com.wangji92.arthas.plugin.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 生命周期小例子
 *
 * @author 汪小哥
 * @date 22-06-2020
 */
@Slf4j
public class TestLifeService implements InitializingBean, DisposableBean, ApplicationContextAware {
    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        log.info("destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet");
    }

    public void initMethod() {
        log.info("initMethod");
    }

    public void destroyMethod() {
        log.info("destroyMethod");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("applicationContextAware");
    }
}
