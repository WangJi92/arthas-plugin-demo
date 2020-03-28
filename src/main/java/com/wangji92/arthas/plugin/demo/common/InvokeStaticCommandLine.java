package com.wangji92.arthas.plugin.demo.common;

import com.wangji92.arthas.plugin.demo.controller.StaticTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 汪小哥
 * @date 28-03-2020
 */
@Component
@Slf4j
public class InvokeStaticCommandLine implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //触发调用一下静态方法，不然没有使用，不会被加载
        log.info("test"+StaticTest.getInvokeStaticName());
    }
}
