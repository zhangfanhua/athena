package com.xx.cortp.utils;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.xx.cortp.entity.ExcelTitile;
import com.xx.cortp.service.impl.EasypoiExcelExportServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Frank.Zhang
 * @date: 2021/1/26 19:09
 * @description:
 */
public class ExcelUtil {

    public static Workbook exportExcel(List<Map<String, Object>> list) {
        Workbook workbook = new HSSFWorkbook();
        for (Map<String, Object> map : list) {
            EasypoiExcelExportServiceImpl service = new EasypoiExcelExportServiceImpl();
            service.createSheetWithList(workbook, (ExportParams) map.get("title"), ExportParams.class, (List<ExcelExportEntity>) map.get("entityList"), (Collection<?>) map.get("data"));
        }
        return workbook;
    }

    public static Map<String, Object> getOneSheet(String sheetName, List<ExcelExportEntity> colList, List<Map<String, Object>> dataList) {
        // 创建sheet1使用得map
        Map<String, Object> sheetMap = new HashMap<>();
        // Sheet1样式
        ExportParams sheet = new ExportParams();
        // 设置sheet得名称
        sheet.setTitle(sheetName);
        sheet.setSheetName(sheetName);
        sheetMap.put("title", sheet);
        sheetMap.put("entityList", colList);
        sheetMap.put("data", dataList);
        return sheetMap;
    }

    public static void workBook(List<Map<String, Object>> sheetsList,String name, HttpServletResponse response) throws IOException {
        Workbook workbook = exportExcel(sheetsList);
        for (int i = 0; i < sheetsList.size(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            List<ExcelExportEntity> colList = (List<ExcelExportEntity>) sheetsList.get(i).get("entityList");
            setCellWith(colList,sheet);
        }
        response.setHeader("content-Type", "application/vnd.ms-excel");
        String fileName = URLEncoder.encode(name,"UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
        workbook.write(response.getOutputStream());
    }

    public static void setCellWith(List<ExcelExportEntity> colList, Sheet sheet) {
        for (int i = 0; i < colList.size(); i++) {
            if (colList.get(i).getList() != null) {
                List<ExcelExportEntity> list = colList.get(i).getList();
                for (int j = 0; j < list.size(); j++) {
                    // 调整每一列宽度
                    sheet.autoSizeColumn((short) i);
                    // 解决自动设置列宽中文失效的问题
                    sheet.setColumnWidth(i, sheet.getColumnWidth(j) * 12 / 10);
                }
            } else {
                // 调整每一列宽度
                sheet.autoSizeColumn((short) i);
                // 解决自动设置列宽中文失效的问题
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 12 / 10);
            }
        }
    }

    public static void export(HttpServletResponse response, List<Map<String, Object>> detailList, List<ExcelTitile> titles, String name) throws IOException {
        List<ExcelTitile> sortedList=titles.stream().sorted(Comparator.comparing(ExcelTitile::getId)).collect(Collectors.toList());
        List<Map<String, Object>> sheetsList = new ArrayList<>();
        List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();
        sortedList.forEach(k->{
            ExcelExportEntity colEntity = new ExcelExportEntity(k.getTitleVal(), k.getTitleKey());
            colEntity.setNeedMerge(true);
            colList.add(colEntity);
        });
        sheetsList.add(ExcelUtil.getOneSheet(name,colList,detailList));
        ExcelUtil.workBook(sheetsList,name,response);
    }

    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }
}
