package com.xx.cortp.config;

import com.xx.cortp.entity.DataSourceInfo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Frank.Zhang
 * @date: 2021/2/21 11:50
 * @description:
 */
public class DataSourceProvider {
    public static HikariDataSource create(DataSourceInfo sourceInfo) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(sourceInfo.getUserName());
        hikariConfig.setPassword(sourceInfo.getPassWord());
        hikariConfig.setJdbcUrl(sourceInfo.getUrl());
        if(sourceInfo.getUrl().contains("mysql")){
            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        }
        if(sourceInfo.getUrl().contains("postgresql")){
            hikariConfig.setDriverClassName("org.postgresql.Driver");
        }
        return new HikariDataSource(hikariConfig);
    }

}
