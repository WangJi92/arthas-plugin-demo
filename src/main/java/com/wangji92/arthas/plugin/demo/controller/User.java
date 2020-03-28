package com.wangji92.arthas.plugin.demo.controller;

import lombok.Data;

/**
 * @author jet
 * @date 25-12-2019
 */
public class User {

    private String name;

    private Long age;

    public User(String name, Long age) {
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
