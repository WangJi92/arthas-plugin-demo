package com.wangji92.arthas.plugin.demo.controller;

import lombok.Data;

/**
 * <p>intro </p>
 * <pre>
 *   {@code
 *       //code example
 *   }
 *  </pre>
 *
 * @author wangji
 * @date 2024/5/19 01:03
 */
@Data
public class TestGeneratesClazz<T,B> {

    private User user2;

    private Integer integer;

    private String st;

    private T user;

    private B  test;
}
