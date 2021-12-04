package com.wangji92.arthas.plugin.demo.controller;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试一下枚举
 */
@Slf4j
public enum TestEnum {

                      COMMON_1("common_1", "common"),
                      COMMON("common", "common"),;

    private String code;
    private String name;

    TestEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final String STATIC_FIELD = "STATIC_FIELD";

    public static void staticMethod() {
        log.info("staticMethod");
    }

    public static class Test {

        public String aa;
    }
}
