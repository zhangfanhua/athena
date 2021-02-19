package com.xx.cortp.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Auther: Frank.Zhang
 * @Date: 2020/4/3 17:30
 * @Description:
 */
@Component
public class BeanContext implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanContext.applicationContext = applicationContext;
    }
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}