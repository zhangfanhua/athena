package com.horen.gateway.config;

import com.horen.gateway.zuul.DBRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

/**
 * @Author AFZ
 * @Date 2020/9/23 10:45
 **/
@Configuration
public class BeanConfig {

    @Autowired
    ZuulProperties zuulProperties;

    @Autowired
    ServerProperties serverProperties;

    @Bean
    public DBRouteLocator routeLocator() {
        DBRouteLocator routeLocator = new DBRouteLocator("/", zuulProperties);
        return routeLocator;
    }

    @Bean
    public AntPathMatcher getAntPathMatcher() {
        return new AntPathMatcher();
    }
}
