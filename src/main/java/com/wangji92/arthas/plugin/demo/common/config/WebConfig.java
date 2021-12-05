package com.wangji92.arthas.plugin.demo.common.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wangji
 * @date 2021/12/4 8:48 下午
 */
@Component
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                     Object handler) throws Exception {

                String reqUri = request.getRequestURI();

                if (!reqUri.contains("checkHead")) {
                    return true;
                }

                String code = request.getHeader("code");
                if (StringUtils.isEmpty(code)) {
                    return false;
                }
                if ("arthas".equals(code)) {
                    return true;
                }
                return false;
            }

        });
    }
}
