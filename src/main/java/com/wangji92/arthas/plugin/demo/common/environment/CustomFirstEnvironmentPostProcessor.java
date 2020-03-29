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
 * 环境变量优先级 比如nacos 中获取、配置中心获取 等等途径 如何排查优先级？
 * @author 汪小哥
 * @date 28-03-2020
 */
public class CustomFirstEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource resource = new ClassPathResource("custom-first.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        environment.getPropertySources().addFirst(new PropertiesPropertySource("customFirst",properties));

    }
}
