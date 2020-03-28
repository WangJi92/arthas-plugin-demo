package com.wangji92.arthas.plugin.demo.service;

/**
 * 测试服务、方便观察多层 trace -E
 * @author 汪小哥
 * @date 28-03-2020
 */
public interface ArthasTestService {

    /**
     * trace -E 效果
     * @param name
     * @return
     */
    String doTraceE(String name);

    /**
     * 没有捕获异常
     * @param number
     */
    void traceException(int number);
}
