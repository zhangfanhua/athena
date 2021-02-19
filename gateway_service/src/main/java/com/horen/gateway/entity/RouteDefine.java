package com.horen.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author AFZ
 * @Date 2020/9/23 10:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDefine implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String path;

    private String serviceId;

    private Integer enableRetry;

    private Integer enabled;

    private Integer stripPrefix;

}
