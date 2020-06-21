package com.wangji92.arthas.plugin.demo.controller;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 汪小哥
 * @date 28-03-2020
 */
@Component
public class StaticTest {

    /**
     *
     * 测试获取 static field 名称
     *
     * ognl  -x  3 '@com.wangji92.arthas.plugin.demo.controller.StaticTest@INVOKE_STATIC_NAME' -c 482503f0
     */
    private static String INVOKE_STATIC_NAME = "INVOKE_STATIC_NAME";

    /**
     * 测试 反射设置static 的信息
     * ognl  -x  3 '@com.wangji92.arthas.plugin.demo.controller.StaticTest@INVOKE_STATIC_FINAL' -c 482503f0
     * ognl -x 3  '#field=@com.wangji92.arthas.plugin.demo.controller.StaticTest@class.getDeclaredField("INVOKE_STATIC_FINAL"),#modifiers=#field.getClass().getDeclaredField("modifiers"),#modifiers.setAccessible(true),#modifiers.setInt(#field,#field.getModifiers() & ~@java.lang.reflect.Modifier@FINAL),#field.setAccessible(true),#field.set(null," ")' -c 482503f0
     */
    private static final String INVOKE_STATIC_FINAL = "INVOKE_STATIC_FINAL";

    /**
     * 测试 处理 static double or long
     * ognl -x 3  '#field=@com.wangji92.arthas.plugin.demo.controller.StaticTest@class.getDeclaredField("INVOKE_STATIC_DOUBLE"),#field.setAccessible(true),#field.set(null,0D)' -c 482503f0
     */
    private static Double INVOKE_STATIC_DOUBLE = 111D;

    /**
     * 演示watch 获取值
     * watch com.wangji92.arthas.plugin.demo.controller.StaticTest * '{params,returnObj,throwExp,@com.wangji92.arthas.plugin.demo.controller.StaticTest@INVOKE_STATIC_LONG}' -n 5 -x 3 '1==1'
     */
    private static Long INVOKE_STATIC_LONG = 1211L;

    /**
     * 演示watch 获取值
     * watch com.wangji92.arthas.plugin.demo.controller.StaticTest * '{params,returnObj,throwExp,target.filedValue}' -n 5 -x 3 '1==1'
     *
     */
    private String filedValue = "wangji92";

    /**
     * 调用非静态的方法才可以在watch 的时候获取非静态的字段
     *
     * watch com.wangji92.arthas.plugin.demo.controller.StaticTest * '{params,returnObj,throwExp,target.filedValue}' -n 5 -x 3 'method.initMethod(),method.constructor!=null || !@java.lang.reflect.Modifier@isStatic(method.method.getModifiers())'
     * @return
     */
    public  String getFieldValue(){
       return this.filedValue;
    }

    /**
     * 调用方法
     *
     * @return
     */
    public static String getInvokeStaticName() {
        return INVOKE_STATIC_NAME;
    }

    /**
     * 调用参数为class = @xxxClass@class 相当于调用静态的方法
     * <p>
     * 备注插件不支持 ，需要记住小知识点
     * <p>
     * ognl  -x  3  '@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeClass(@com.wangji92.arthas.plugin.demo.controller.StaticTest@class)' -c 482503f0
     * ognl  -x  3  '@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeClass(@java.lang.Object@class)' -c 316bc132
     *
     * @param classN
     * @return
     */
    public static String invokeClass(Class classN) {
        if (classN == null) {
            return "";
        }
        return classN.getName();
    }

    /**
     * 对于class类型的参数进行了增强处理
     * ognl  -x  3  '@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeClass1(@com.wangji92.arthas.plugin.demo.controller.User@class)' -c 316bc132
     * @param classN
     * @return
     */
    public static String invokeClass1(Class<User> classN) {
        if (classN == null) {
            return "";
        }
        return classN.getName();
    }


    /**
     * array 场景  参考：https://blog.csdn.net/u010634066/article/details/101013479
     * 如何使用ognl传递方法？ognl按照表达式逗号分割顺序执行命令，支持链式调用，#开头在ognl 里面是变量全局的，可以传递。
     * 复杂的参数场景 #listObject={} ,#mapObject= #{"key","value"}  #array=new java.lang.String[]{"1"}
     * <p>
     * ognl  -x  3  '@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeStaticMethodArray(new java.lang.String[]{"wangji"})' -c 316bc132
     * ognl  -x  3  '#array=new java.lang.String[]{"wangji"},@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeStaticMethodArray(#array)' -c 316bc132
     *
     * @param arrays
     * @return
     */
    public static String invokeStaticMethodArray(String[] arrays) {
        if (arrays == null) {
            return "";
        }
        return Arrays.toString(arrays);
    }


    /**
     * 调用静态方法 参数为list
     * 复杂的参数场景 #listObject={} ,#mapObject= #{"key","value"}  #array=new java.lang.String[]{"1"}
     * <p>
     * ognl  -x  3  '@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeStaticMethodParamList({"wangji"})' -c 316bc132
     *
     * @param list
     * @return
     */
    public static String invokeStaticMethodParamList(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "EMPTY";
        }
        return list.toString();
    }

    /**
     * 调用静态方法 参数为map
     * 复杂的参数场景 #listObject={} ,#mapObject= #{"key","value"}  #array=new java.lang.String[]{"1"}
     * ognl  -x  3  '@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeStaticMethodParamMap(#{"name":"wangji"})' -c 316bc132
     *
     * @param map
     * @return
     */
    public static String invokeStaticMethodParamMap(Map<String, String> map) {
        if (map == null) {
            return "EMPTY";
        }
        return map.toString();
    }

    /**
     * 参考：https://blog.csdn.net/u010634066/article/details/101013479
     * 如何使用ognl传递方法？ognl按照表达式逗号分割顺序执行命令，支持链式调用，#开头在ognl 里面是变量全局的，可以传递。
     * <p>
     * ognl  -x  3  '@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeStaticMethodParamObjUser(new com.wangji92.arthas.plugin.demo.controller.User("wangji",27L))' -c e374b99
     * ognl  -x  3  '#user=new com.wangji92.arthas.plugin.demo.controller.User(),#user.setName("wangji"),#user.setAge(27L),@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeStaticMethodParamObjUser(#user)' -c e374b99
     * 调用静态方法 是一个对象
     *
     * @param user
     * @return
     */
    public static String invokeStaticMethodParamObjUser(User user) {
        if (user == null) {
            return "EMPTY";
        }
        return user.toString();
    }

    /**
     * 复杂的参数场景 #listObject={} ,#mapObject= #{"key","value"}  #array=new java.lang.String[]{"1"}
     * <p>
     * ognl  -x  3  '#user=new com.wangji92.arthas.plugin.demo.controller.User(),#user.setName("wangji"),#user.setAge(27L),@com.wangji92.arthas.plugin.demo.controller.StaticTest@invokeStaticMethodParamObjListUser(0,{#user,#user})' -c e374b99
     *
     * @param number
     * @param names
     * @return
     */
    public static String invokeStaticMethodParamObjListUser(Integer number, List<User> names) {
        if (number == null) {
            number = 1;
        }
        if (names == null) {
            names = Collections.EMPTY_LIST;
        }
        return number + names.toString();
    }
}
