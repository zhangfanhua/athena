package com.xx.cortp.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 10:32
 * @description:
 */
@Data
public class ClassInfo {
    private String schema;
    private String tableName;
    private String className;
    private String classComment;
    private List<FieldInfo> fieldList;
}
