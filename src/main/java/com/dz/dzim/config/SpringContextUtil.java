package com.dz.dzim.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author 解决ServerEndpoint 无法注入问题
 * @className SpringContextUtil
 * @description TODO
 * @date 2021/1/26 23:01
 */
@Component
@Lazy(false)
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return APPLICATION_CONTEXT;
    }
}
