package com.wangji92.arthas.plugin.demo.common;

import com.alibaba.fastjson.JSON;
import com.wangji92.arthas.plugin.demo.common.profile.HotCode;
import com.wangji92.arthas.plugin.demo.controller.OuterClass;
import com.wangji92.arthas.plugin.demo.controller.StaticTest;
import com.wangji92.arthas.plugin.demo.controller.TestEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 汪小哥
 * @date 28-03-2020
 */
@Component
@Slf4j
public class InvokeStaticCommandLine implements CommandLineRunner {

    @Autowired
    private HotCode hotCode;

    @Override
    public void run(String... args) throws Exception {
        //触发调用一下静态方法，不然没有使用，不会被加载
        log.info("test" + StaticTest.getInvokeStaticName());
        new Thread(hotCode::hotMethodRun, "hotCode").start();

        OuterClass.InnerClass innerClass = new OuterClass().new InnerClass();
        innerClass.anonymousClassRun();
        OuterClass.InnerClass.InnerInnerClass innerInnerClass = innerClass.new InnerInnerClass();
        innerInnerClass.anonymousInnerInnerClassRun();
        log.info(TestEnum.COMMON.getCode());

        JSON.toJSON(TestEnum.COMMON);


        log.info("OuterClass.StaticInnerClass.getStaticInnerName() {}", OuterClass.StaticInnerClass.getStaticInnerName());
    }
}
