package com.xx.cortp.entity;

import lombok.Data;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 10:33
 * @description:
 */
@Data
public class ParamInfo {
    private String tableName;
    private String tableSql;
    private String authorName;
    private String packageName;
    private String returnUtil;
    private String nameCaseType;
    private String tinyintTransType;
    private String dataType;
    private boolean swagger;
    private String customUrl;

    @Data
    public static class NAME_CASE_TYPE{
        public static String CAMEL_CASE="CamelCase";
        public static String UNDER_SCORE_CASE="UnderScoreCase";
        public static String UPPER_UNDER_SCORE_CASE="UpperUnderScoreCase";
    }
}
