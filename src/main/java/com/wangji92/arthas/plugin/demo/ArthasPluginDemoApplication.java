package com.wangji92.arthas.plugin.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArthasPluginDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArthasPluginDemoApplication.class, args);
    }

}
