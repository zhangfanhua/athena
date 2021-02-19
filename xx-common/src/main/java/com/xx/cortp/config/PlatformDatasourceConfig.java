package com.xx.cortp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Auther: Frank.Zhang
 * @Date: 2020/2/25 16:10
 * @Description:
 */
@Configuration
@MapperScan(basePackages = PlatformDatasourceConfig.PACKAGE, sqlSessionFactoryRef = "platformSqlSessionFactory")
public class PlatformDatasourceConfig {

    static final String PACKAGE = "com.xx.cortp.repository.platform";

    @Bean(name = "platformDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.platform")
    public HikariDataSource dataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "platformTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean(name = "platformSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("platformDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/platform/*.xml"));
        return sessionFactory.getObject();
    }
}
