package com.xx.cortp.entity;

import lombok.Data;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 10:32
 * @description:
 */
@Data
public class FieldInfo {

    private String columnName;
    private String fieldName;
    private String fieldClass;
    private String fieldComment;

}
