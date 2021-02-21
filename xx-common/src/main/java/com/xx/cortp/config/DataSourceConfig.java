package com.xx.cortp.config;

import com.xx.cortp.entity.DataSourceInfo;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Frank.Zhang
 * @date: 2021/2/21 11:46
 * @description:
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
    @Value("${spring.datasource.platform.jdbcUrl}")
    private String url;
    @Value("${spring.datasource.platform.username}")
    private String userName;
    @Value("${spring.datasource.platform.password}")
    private String password;
    @Bean
    @ConditionalOnMissingBean(MyHikariDataSource.class)
    public MyHikariDataSource dataSource() {
        DataSourceInfo info = new DataSourceInfo();
        info.setUrl(url);
        info.setUserName(userName);
        info.setPassWord(password);
        HikariDataSource dataSource = DataSourceProvider.create(info);
        MyHikariDataSource hikariCPDataSource = new MyHikariDataSource();
        hikariCPDataSource.updateDataSourceMap("1", dataSource);
        return hikariCPDataSource;
    }
}
