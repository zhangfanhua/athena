package com.horen.gateway.zuul;

import com.horen.gateway.service.RouteDefineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DBRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    final static Logger logger = LoggerFactory.getLogger(DBRouteLocator.class);

    ZuulProperties properties;

    @Autowired
    RouteDefineService routeDefineService;

    public DBRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulRoute> locateRoutes() {
        Map<String, ZuulRoute> routesMap = new HashMap<>();
        //从db中加载路由信息
        routesMap.putAll(loadRoutesFromDB());
        //优化一下配置
        Map<String, ZuulRoute> values = new HashMap<>();
        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(properties.getPrefix())) {
                path = properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulRoute> loadRoutesFromDB() {
        Map<String, ZuulRoute> routes = new LinkedHashMap<>();
        List<Route> results = routeDefineService.getRouteList();
        if (results != null) {
            for (Route result : results) {
                ZuulRoute zuulRoute = new ZuulRoute();
                try {
                    BeanUtils.copyProperties(result, zuulRoute);
                } catch (Exception ex) {
                    logger.error("loadRoutesFromDB", ex);
                }
                routes.put(zuulRoute.getPath(), zuulRoute);
            }
        }
        return routes;
    }

}
