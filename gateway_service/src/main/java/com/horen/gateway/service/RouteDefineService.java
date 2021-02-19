package com.horen.gateway.service;

import com.horen.gateway.entity.RouteDefine;
import com.horen.gateway.mapper.RouteDefineMapper;
import com.horen.gateway.zuul.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author AFZ
 * @Date 2020/9/23 10:50
 **/
@Service
public class RouteDefineService {

    @Autowired
    RouteDefineMapper routeDefineMapper;

    public List<Route> getRouteList() {
        List<RouteDefine> routeDefines = routeDefineMapper.getRouteDefineList();
        List<Route> routeList = new ArrayList<>();
        routeDefines.forEach(k -> {
            Route route = new Route();
            route.setId(k.getId());
            route.setPath(k.getPath());
            route.setServiceId(k.getServiceId());
            route.setEnabled(k.getEnabled() != null && k.getEnabled() == 1 ? true : false);
            route.setEnableRetry(k.getEnableRetry() != null && k.getEnableRetry() == 1 ? true : false);
            route.setStripPrefix(k.getStripPrefix() != null && k.getStripPrefix() == 0 ? false : true);
            routeList.add(route);
        });
        return routeList;
    }
}
