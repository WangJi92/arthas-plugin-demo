package com.wangji92.arthas.plugin.demo.service.impl;

import com.wangji92.arthas.plugin.demo.service.ArthasTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author 汪小哥
 * @date 28-03-2020
 */
@Slf4j
@Component
public class ArthasTestServiceImpl implements ArthasTestService {
    @Override
    public String doTraceE(String name) {
        try {
            //1、thread 1 s
            Thread.sleep(1000);
            //2 get a currentTimeMillis
            if(StringUtils.isEmpty(name)){
                name = System.currentTimeMillis()+"";
            }
            //3、use trace -E is very well
            return Thread.currentThread().getName() + name;
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        }
        return "";
    }

    @Override
    public void traceException(int number) {
        int i = number / 0;
        //maybe old code happen before error,but not capture exception，log not found
        //use trace is very useful
        log.info(i + "");
    }
}
