package com.wangji92.arthas.plugin.demo.common.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 *
 * ./profiler.sh -d 10 -e alloc -f output-alloc.svg -t 1786
 * ./profiler.sh -d 10 -e cpu -f output-cpu.svg  --all-user 1786
 * ./profiler.sh -d 10  -f output-cpu.svg  --all-user 1786
 * ./profiler.sh -d 10 -e com.wangji92.arthas.plugin.demo.common.profile.HotCode.hotMethod1 -f output-method.svg  --all-user 1786
 * ./profiler.sh -d 10   -f output-cpu.svg  --all-user 1786
 * ./profiler.sh -d 10 -e itimer -f output-itimer.svg  --all-user 1786
 * ./profiler.sh -d 10 -e wall -f output-wall.svg  -t 1786
 * ./profiler.sh list  1786
 * ./profiler.sh
 * 模拟热点代码
 * 参考文章 : https://www.jianshu.com/p/918e1dce61cd
 *
 * @author 汪小哥
 * @date 23-06-2020
 */
@Component
@Slf4j
public class HotCode {

    private static volatile int value;

    public void hotMethodRun() {
        while (true) {
            //log.info("hotMethodScheduled begin");
            HotCode.hotMethod1();
            HotCode.hotMethod2();
            HotCode.hotMethod3();
            HotCode.allocate();
            //log.info("hotMethodScheduled end");
        }
    }

    private static Object array;

    /**
     * 生成 大长度的数组
     */
    private static void allocate() {
        array = new int[6 * 100];
        array = new Integer[6 * 100];
    }


    /**
     * 生成一个UUID
     */
    private static void hotMethod3() {
        ArrayList<String> list = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-", "");
        list.add(str);
    }

    /**
     * 数字累加
     */
    private static void hotMethod2() {
        value++;
    }

    /**
     * 生成一个随机数
     */
    private static void hotMethod1() {
        Random random = new Random();
        int anInt = random.nextInt();
    }


}
