package com.xx.cortp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Frank.Zhang
 * @date: 2021/2/25 10:03
 * @description:
 */
@Data
public class ExcelTitile {

    /**
     * 排序ID 从1开始
     */
    @ApiModelProperty(value = "排序ID")
    private int id;
    /**
     * excel 头部key 英文
     */
    @ApiModelProperty(value = "excel 头部key")
    private String titleKey;
    /**
     * excel 头部val 中文
     */
    @ApiModelProperty(value = "excel 头部val")
    private String titleVal;
}
