package com.xx.cortp.repository.platform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Frank.Zhang
 * @date: 2021/2/20 10:35
 * @description:
 */
@Mapper
@Repository
public interface GeneratorMapper {

    /**
     * 查询所有
     *
     * @param tableName
     * @return list
     */
    List<String> findTables(@Param("tableName") String tableName);

    /**
     * 查询表的所有列
     *
     * @param tableName
     * @return
     */
    List<Map<String, String>> findTableColumn(@Param("tableName") String tableName);

    /**
     * 查询mysql所有表
     *
     * @param tableName
     * @return
     */
    List<String> findMysqlTables(@Param("tableName") String tableName);

    /**
     * mysql查询表的所有列
     *
     * @param tableName
     * @return
     */
    List<Map<String, String>> findMysqlTableColumn(@Param("tableName") String tableName);
}
