package com.wangji92.arthas.plugin.demo.common.config;

import com.wangji92.arthas.plugin.demo.controller.TestLifeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生命周期优先级
 * @author 汪小哥
 * @date 22-06-2020
 */
@Configuration
public class TestLifeConfiguration {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public TestLifeService testLifeService() {
        return new TestLifeService();
    }
}
