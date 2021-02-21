package com.xx.cortp.service;

import com.xx.cortp.entity.DataSourceInfo;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 11:24
 * @description:
 */
public interface GeneratorService {

    /**
     * 生成代码
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    Map<String,String> getResultByParams(Map<String, Object> params) throws IOException, TemplateException;

    /**
     * 查询所有表
     * @param tableName
     * @return
     */
    List<String> getTables(String tableName);

    /**
     * 查询表详情
     * @param tableName
     * @return string
     */
    String getTableInfo(String tableName);

    /**
     * 重新配置数据源
     * @param userName
     * @param password
     * @param url
     */
    void reload(DataSourceInfo info);

}
