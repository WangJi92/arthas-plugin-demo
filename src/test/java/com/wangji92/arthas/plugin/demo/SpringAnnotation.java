package com.wangji92.arthas.plugin.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * @author 汪小哥
 * @date 05-07-2020
 */
@Slf4j
public class SpringAnnotation {
    public interface AnnotationsServiceInterface {

        @Transactional
        void test();

    }

    public class AnnotationsServiceInterfaceImpl implements AnnotationsServiceInterface {

        @Override
        public void test() {

        }
    }

    @Test
    public void testInterface() throws Exception {
        AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource(true);
        Method test = ReflectionUtils.findMethod(AnnotationsServiceImpl.class, "test");
        TransactionAttribute transactionAttribute = attributeSource.getTransactionAttribute(test,
                AnnotationsServiceInterface.class);

        log.info("transactionAttribute={}", transactionAttribute.toString());

    }

    @Test
    public void testInterfaceImpl() throws Exception {
        AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource(true);
        Method test = ReflectionUtils.findMethod(AnnotationsServiceImpl.class, "test");
        TransactionAttribute transactionAttribute = attributeSource.getTransactionAttribute(test,
                AnnotationsServiceInterfaceImpl.class);

        log.info("transactionAttribute={}", transactionAttribute.toString());

    }

    @Transactional(rollbackFor = IndexOutOfBoundsException.class, propagation = Propagation.REQUIRES_NEW)
    public class AnnotationsService {

        public void test() {

        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public class AnnotationsServiceImpl extends AnnotationsService {

        @Override
        public void test() {

        }
    }

    @Test
    public void testServices() throws Exception {
        AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource(true);
        Method test = ReflectionUtils.findMethod(AnnotationsServiceImpl.class, "test");
        TransactionAttribute transactionAttribute = attributeSource.getTransactionAttribute(test,
                AnnotationsService.class);

        log.info("transactionAttribute={}", transactionAttribute.toString());

    }

    @Test
    public void testServicesImpl() throws Exception {
        AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource(true);
        Method test = ReflectionUtils.findMethod(AnnotationsServiceImpl.class, "test");
        TransactionAttribute transactionAttribute = attributeSource.getTransactionAttribute(test,
                AnnotationsServiceImpl.class);

        log.info("transactionAttribute={}", transactionAttribute.toString());

    }

    @Test
    @GetMapping(value = "/GetMapping", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void test1() throws NoSuchMethodException {
        Method method = ReflectUtils.findDeclaredMethod(
                SpringAnnotation.class, "test", null);

        // AnnotationUtils 不支持注解属性覆盖
        RequestMapping requestMappingAnn1 = AnnotationUtils.getAnnotation(method, RequestMapping.class);
        Assert.assertEquals(new String[]{}, requestMappingAnn1.value());
        Assert.assertEquals(new String[]{}, requestMappingAnn1.consumes());

        // AnnotatedElementUtils 支持注解属性覆盖
        RequestMapping requestMappingAnn2 = AnnotatedElementUtils.getMergedAnnotation(method, RequestMapping.class);
        Assert.assertEquals(new String[]{"/GetMapping"}, requestMappingAnn2.value());
        Assert.assertEquals(new String[]{MediaType.APPLICATION_JSON_VALUE}, requestMappingAnn2.consumes());
    }


    @Test
    @GetMapping(value = "/GetMapping", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void test() throws NoSuchMethodException {
        Method method = ReflectUtils.findDeclaredMethod(SpringAnnotation.class, "test", null);

        // AnnotationUtils 不支持注解属性覆盖
        RequestMapping requestMappingAnn1 = AnnotationUtils.getAnnotation(method, RequestMapping.class);

        Class<?> targetClass = AopUtils.getTargetClass(requestMappingAnn1);
        Assert.assertEquals(new String[]{}, requestMappingAnn1.value());
        Assert.assertEquals(new String[]{}, requestMappingAnn1.consumes());

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetClass(AnnotationsServiceImpl.class);
        proxyFactory.setTarget(new AnnotationsServiceImpl());
        Object proxy = proxyFactory.getProxy();

        Class<?> targetClass1 = AopUtils.getTargetClass(proxy);

        Method test = ReflectionUtils.findMethod(targetClass1, "test");

        AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource(true);
        TransactionAttribute transactionAttribute = attributeSource.getTransactionAttribute(test, targetClass1);

        // AnnotatedElementUtils 支持注解属性覆盖
        RequestMapping requestMappingAnn2 = AnnotatedElementUtils.getMergedAnnotation(method, RequestMapping.class);
        Assert.assertEquals(new String[]{"/GetMapping"}, requestMappingAnn2.value());
        Assert.assertEquals(new String[]{MediaType.APPLICATION_JSON_VALUE}, requestMappingAnn2.consumes());

        GetMapping getMapping = AnnotationUtils.getAnnotation(method, GetMapping.class);

        GetMapping getMapping1 = AnnotatedElementUtils.getMergedAnnotation(method, GetMapping.class);
    }

}
