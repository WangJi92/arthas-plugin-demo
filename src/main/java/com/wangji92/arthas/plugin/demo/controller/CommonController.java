package com.wangji92.arthas.plugin.demo.controller;

import com.wangji92.arthas.plugin.demo.service.ArthasTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * arthas 体验demo
 *
 * @author 汪小哥
 * @date 28-03-2020
 */
@Controller
@RequestMapping("/")
@Slf4j
public class CommonController {

    @Autowired
    private ArthasTestService  arthasTestService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private StaticTest         staticTest;

    public static final String WATCH_STATIC_VALUE = "wangji";

    private String             watchValue         = "wangji";

    /**
     * https://github.com/alibaba/arthas/issues/71  fastjson 转换复杂对象进行调用
     * https://github.com/WangJi92/arthas-idea-plugin/issues/127  复杂类型参数的支持
     * @param param
     * 处理基本类型参数、没有泛型
     * {@code
     *  vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam(@com.alibaba.fastjson.JSON@parseObject("{\"name\":\" \",\"age\":0}",@com.wangji92.arthas.plugin.demo.controller.User@class))'  -c 6f54e410
     * }
     * @return
     */
    public Object testParam(User param) {
        return param;
    }

    /**
     * 处理基本集合 Map 参数
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((#{"_AR_": @com.alibaba.fastjson.JSON@parseObject("{\"name\":\" \",\"age\":0}",@com.wangji92.arthas.plugin.demo.controller.User@class)}))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(Map<String,User> param) {
        return param;
    }

    /**
     * 处理基本集合List 参数
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam({@com.alibaba.fastjson.JSON@parseObject("{\"name\":\" \",\"age\":0}",@com.wangji92.arthas.plugin.demo.controller.User@class)})'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(List<User> param) {
        return param;
    }

    /**
     * 泛型内部有通配符
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam2({@com.alibaba.fastjson.JSON@parseObject("{\"name\":\" \",\"age\":0}",@com.wangji92.arthas.plugin.demo.controller.User@class)})'  -c c212d8b
     * }
     * @param param
     * @return
     */
    public Object testParam2(List<? extends User> param) {
        return param;
    }

    public Object testParam2(Map<String,? extends User> param) {
        return param;
    }

    /**
     * 处理基本集合Set参数
     * {@code
     *      vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((#set=new java.util.HashSet(),#set.add(@com.alibaba.fastjson.JSON@parseObject("{\"name\":\" \",\"age\":0}",@com.wangji92.arthas.plugin.demo.controller.User@class)),#set))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(Set<User> param) {
        return param;
    }

    /**
     * 处理特殊JDK Map
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((#@java.util.concurrent.ConcurrentHashMap@{"_AR_": @com.alibaba.fastjson.JSON@parseObject("{\"name\":\" \",\"age\":0}",@com.wangji92.arthas.plugin.demo.controller.User@class)}))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(ConcurrentHashMap<String,User> param) {
        return param;
    }

    /**
     * 出来特殊JDK List
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((#list=new java.util.concurrent.CopyOnWriteArrayList(),#list.add(@com.alibaba.fastjson.JSON@parseObject("{\"name\":\" \",\"age\":0}",@com.wangji92.arthas.plugin.demo.controller.User@class)),#list))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(CopyOnWriteArrayList<User> param) {
        return param;
    }

    /**
     * 处理特殊JDK 参数
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam(@com.alibaba.fastjson.JSON@parseObject("\"2024-05-26 00:31:23 +0800\"",@java.util.Calendar@class))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(Calendar param) {
        return param;
    }


    /**
     * 处理内部类数组
     *{@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((new com.wangji92.arthas.plugin.demo.controller.OuterClass$InnerClass[]{@com.alibaba.fastjson.JSON@parseObject("{\"innerName\":\" \",\"innerAge\":0}",@com.wangji92.arthas.plugin.demo.controller.OuterClass$InnerClass@class)}))'  -c 6f54e410
     *}
     * @param param
     * @return
     */
    public Object testParam(OuterClass.InnerClass[] param) {
        return param;
    }

    /**
     * 处理 CLazz 类型内部类
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((@com.wangji92.arthas.plugin.demo.controller.OuterClass$InnerClass@class))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(Class<OuterClass.InnerClass> param) {
        return param;
    }


    /**
     * 处理内部类枚举参数
     * {@code
     *      vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam(@com.wangji92.arthas.plugin.demo.controller.OuterClass$Type@TEST)'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(OuterClass.Type param) {
        return param;
    }


    /**
     * 处理基本数组
     * {@code
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((new java.lang.Integer[]{0}))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(Integer[] param) {
        return param;
    }


    /**
     *  处理这种泛型复杂参数 TestGeneratesClazz<String,Map<String,User>> test
     *  1、ognl 本身不支持泛型参数
     *  2、通过Json 构建外层对象 TestGeneratesClazz
     *  3、获取TestGeneratesClazz所有泛型参数
     *  4、遍历TestGeneratesClazz 所有字段 包含了泛型参数 且非基本类型，通过判断是否有set方法进行赋值 同理赋值使用json 构造
     *  5、构建脚本 差异化解决无法处理泛型的问题..
     * {@code 
     *     vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController  --express 'instances[0].testParam((#p=@com.alibaba.fastjson.JSON@parseObject("{\"user2\":{\"name\":\" \",\"age\":0},\"integer\":0,\"st\":\" \",\"user\":\" \",\"test\":{\"_key_\":{\"name\":\" \",\"age\":0}}}",@com.wangji92.arthas.plugin.demo.controller.TestGeneratesClazz@class),(#p.setUser(@com.alibaba.fastjson.JSON@parseObject("{\"user2\":{\"name\":\" \",\"age\":0},\"integer\":0,\"st\":\" \",\"user\":\" \",\"test\":{\"_key_\":{\"name\":\" \",\"age\":0}}}",@com.wangji92.arthas.plugin.demo.controller.TestGeneratesClazz@class))),(#p.setTest(@com.alibaba.fastjson.JSON@parseObject("{\"user2\":{\"name\":\" \",\"age\":0},\"integer\":0,\"st\":\" \",\"user\":\" \",\"test\":{\"_key_\":{\"name\":\" \",\"age\":0}}}",@com.wangji92.arthas.plugin.demo.controller.TestGeneratesClazz@class))),#p))'  -c 6f54e410
     * }
     * @param param
     * @return
     */
    public Object testParam(TestGeneratesClazz<String,Map<String,User>> param) {
        return param;
    }



    /**
     * tt 测试 or 通过ognl 调用spring context getBean 调用处理 ognl -x 3
     * '#springContext=@com.wangji92.arthas.plugin.demo.common.ApplicationContextProvider@context,#springContext.getBean("commonController").getRandomInteger()'
     * -c e374b99
     * <p>
     * 记录时间隧道，重新触发一次 tt -t com.wangji92.arthas.plugin.demo.controller.CommonController getRandomInteger -n 5 tt -p -i
     * 1000
     *
     * @return
     */
    @RequestMapping("/getRandomInteger")
    @ResponseBody
    public Integer getRandomInteger() {
        return new Random().nextInt(1000);

    }

    /**
     * use trace -E trace -E
     * com.wangji92.arthas.plugin.demo.controller.CommonController|com.wangji92.arthas.plugin.demo.service.ArthasTestService
     * traceE|doTraceE -n 5
     * <p>
     * ##包含jdk的调用 trace -E
     * com.wangji92.arthas.plugin.demo.controller.CommonController|com.wangji92.arthas.plugin.demo.service.ArthasTestService
     * traceE|doTraceE -n 5 --skipJDKMethod false
     *
     * @param name
     * @return
     */
    @RequestMapping("/trace/{name}")
    @ResponseBody
    public String traceE(@PathVariable String name) {
        if (StringUtils.isEmpty(name)) {
            name = "汪小哥";
        } else {
            name = name + "汪小哥";
        }
        return arthasTestService.doTraceE(name);
    }

    /**
     * 排查异常场景 trace com.wangji92.arthas.plugin.demo.controller.CommonController traceException -n 5 watch
     * com.wangji92.arthas.plugin.demo.controller.CommonController traceException '{params,returnObj,throwExp}' -n 5 -x
     * 3
     *
     * @return
     */
    @RequestMapping("traceException")
    @ResponseBody
    public String traceException() {
        arthasTestService.traceException(1);
        return "ok";
    }

    /**
     * 环境变量优先级问题排查 目前只支持获取静态static spring context 获取所有的环境变量 按照优先级排序 ognl -x 3
     * '#springContext=@com.wangji92.arthas.plugin.demo.common.ApplicationContextProvider@context,#allProperties={},#standardServletEnvironment=#propertySourceIterator=#springContext.getEnvironment(),#propertySourceIterator=#standardServletEnvironment.getPropertySources().iterator(),#propertySourceIterator.{#key=#this.getName(),#allProperties.add("
     * "),#allProperties.add("------------------------- name:"+#key),#this.getSource() instanceof java.util.Map
     * ?#this.getSource().entrySet().iterator.{#key=#this.key,#allProperties.add(#key+"="+#standardServletEnvironment.getProperty(#key))}:#{}},#allProperties'
     * -c e374b99
     * <p>
     * 选中 custom.name 获取当前的变量的信息 ognl -x 3
     * '#springContext=@com.wangji92.arthas.plugin.demo.common.ApplicationContextProvider@context,#springContext.getEnvironment().getProperty("custom.name")'
     * -c e374b99
     *
     * @return
     */
    @RequestMapping("environmentPriority")
    @ResponseBody
    public String environmentPriority() {
        return applicationContext.getEnvironment().getProperty("custom.name");
    }

    /**
     * 复杂参数调用 场景 static spring context ognl -x 3 '#user=new
     * com.wangji92.arthas.plugin.demo.controller.User(),#user.setName("wangji"),#user.setAge(27L),#springContext=@com.wangji92.arthas.plugin.demo.common.ApplicationContextProvider@context,#springContext.getBean("commonController").complexParameterCall(#{"wangji":#user})'
     * -c e374b99
     * <p>
     * watch get spring context 备注 需要调用一次方法 watch -x 3 -n 1 org.springframework.web.servlet.DispatcherServlet doDispatch
     * '#user=new
     * com.wangji92.arthas.plugin.demo.controller.User(),#user.setName("wangji"),#user.setAge(27L),@org.springframework.web.context.support.WebApplicationContextUtils@getWebApplicationContext(params[0].getServletContext()).getBean("commonController").complexParameterCall(#{"wangji":#user})'
     * <p>
     * tt get spring context ，only first get time index ok tt -w '#user=new
     * com.wangji92.arthas.plugin.demo.controller.User(),#user.setName("wangji"),#user.setAge(27L),target.getApplicationContext().getBean("commonController").complexParameterCall(#{"wangji":#user})'
     * -x 3 -i 1000
     *
     * @return
     */
    @RequestMapping("complexParameterCall")
    @ResponseBody
    public String complexParameterCall(@RequestBody Map<String, User> names) {
        if (names == null) {
            return "EMPTY";
        }
        return names.toString();
    }

    /**
     * 1、将光标放置在需要观察的值的字段上面 比如下面的这个获取静态字段的值,无论是静态字段还是实例字段都是可以支持的！ watch
     * com.wangji92.arthas.plugin.demo.controller.StaticTest *
     * '{params,returnObj,throwExp,@com.wangji92.arthas.plugin.demo.controller.StaticTest@INVOKE_STATIC_LONG}' -n 5 -x 3
     * '1==1'
     * <p>
     * watch com.wangji92.arthas.plugin.demo.controller.CommonController *
     * '{params,returnObj,throwExp,target.arthasTestService}' -n 5 -x 3 '1==1'
     * <p>
     * 2、触发一下这个类的某个方法的调用 eg: 比如这里调用这个 http://localhost:8080/watchField 3、即可查看到具体的信息
     *
     * @return
     */
    @RequestMapping("watchField")
    @ResponseBody
    public String watchField() {
        return StaticTest.getInvokeStaticName();
    }

    /**
     * 调用非静态的方法才可以在watch 的时候获取非静态的字段 watch com.wangji92.arthas.plugin.demo.controller.StaticTest *
     * '{params,returnObj,throwExp,target.filedValue}' -n 5 -x 3 'method.initMethod(),method.constructor!=null ||
     * !@java.lang.reflect.Modifier@isStatic(method.method.getModifiers())'
     *
     * @return
     */
    @RequestMapping("watchNoStaticField")
    @ResponseBody
    public String watchNoStaticField() {
        return staticTest.getFieldValue();
    }

    @RequestMapping("AnonymousClass")
    @ResponseBody
    public String anonymousClass() {
        Runnable x = new Runnable() {

            @Override
            public void run() {
                log.info(this.getClass().getName());
            }
        };
        x.run();
        return "ok";
    }

    @RequestMapping("innerAnonymousClass")
    @ResponseBody
    public String innerAnonymousClass() {
        OuterClass.InnerClass innerClass =  new OuterClass.InnerClass();
        innerClass.anonymousClassRun();
        innerClass.getInnerAge();
        OuterClass.InnerClass.InnerInnerClass innerInnerClass = innerClass.new InnerInnerClass();
        innerInnerClass.getInnerInnerAge();
        innerInnerClass.anonymousInnerInnerClassRun();
        return "ok";
    }

    /**
     * vmtool -x 3 --action getInstances --className com.wangji92.arthas.plugin.demo.controller.CommonController
     * --express 'instances[0].testEnum(@com.wangji92.arthas.plugin.demo.controller.TestEnum@COMMON_1)' -c 59a6bc53
     * 先测试一下枚举
     * 
     * @param testEnum
     * @return
     */
    @RequestMapping("testEnum")
    @ResponseBody
    public String testEnum(TestEnum testEnum) {
        if (testEnum != null) {
            return testEnum.getName();
        }
        return "ok";
    }

    /**
     * check head
     * @return
     */
    @RequestMapping("checkHead")
    @ResponseBody
    public String checkHead() {

        return "ok";
    }


}
