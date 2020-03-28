package com.wangji92.arthas.plugin.demo.common.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * 环境变量优先级
 * @author 汪小哥
 * @date 28-03-2020
 */
public class CustomLastEnvironmentPostProcessor  implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource resource = new ClassPathResource("custom-last.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        environment.getPropertySources().addLast(new PropertiesPropertySource("customLast",properties));
    }
}
