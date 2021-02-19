package com.horen.gateway.mapper;

import com.horen.gateway.entity.RouteDefine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author AFZ
 * @Date 2020/9/23 10:54
 **/
@Component
@Mapper
public interface RouteDefineMapper {

    @Select("select * from ac.route_define")
    List<RouteDefine> getRouteDefineList();

}
