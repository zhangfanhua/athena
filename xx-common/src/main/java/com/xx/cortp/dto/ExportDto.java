package com.xx.cortp.dto;

import com.xx.cortp.entity.ExcelTitile;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Frank.Zhang
 * @date: 2021/2/25 17:16
 * @description:
 */
@Data
public class ExportDto {
    List<Map<String, Object>> detailList;
    List<ExcelTitile> titles;
    String name;
}
