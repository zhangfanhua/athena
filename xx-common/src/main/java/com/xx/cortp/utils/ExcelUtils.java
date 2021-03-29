package com.xx.cortp.utils;//package com.mmhw.hi.common.utils;
//
//import com.mmhw.hi.utils.ConvertUtil;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author Frank.zhang
// * @Date: 2019/7/1 16:18
// * @Description: 导入导出Excel
// */
//public class ExcelUtils {
//
//    /**
//     * 导入Excel
//     * @param fileName
//     * @throws Exception
//     */
//    public static List readExcel(String fileName) throws Exception{
//
//        InputStream is = new FileInputStream(new File(fileName));
//        Workbook hssfWorkbook = null;
//        if (fileName.endsWith("xlsx")){
//            //Excel 2007
//            hssfWorkbook = new XSSFWorkbook(is);
//        }else if (fileName.endsWith("xls")){
//            //Excel 2003
//            hssfWorkbook = new HSSFWorkbook(is);
//
//        }
//        // HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
//        List<Map> list = new ArrayList<Map>();
//        // 循环工作表Sheet
//        for (int numSheet = 0; numSheet <hssfWorkbook.getNumberOfSheets(); numSheet++) {
//            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
//            if (hssfSheet == null) {
//                continue;
//            }
//            // 循环行Row
//            System.out.println(hssfSheet.getLastRowNum()+"--------------");
//            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
//                //HSSFRow hssfRow = hssfSheet.getRow(rowNum);
//                Row hssfRow = hssfSheet.getRow(rowNum);
//                Cell cell = hssfRow.getCell(1);
//                if (hssfRow != null && cell != null) {
//                    Map map = new HashMap();
//                    String org_name = hssfRow.getCell(1).toString();
//                    String org_address = hssfRow.getCell(2).toString();
//                    String lon_lat = hssfRow.getCell(3).toString();
//                    //这里是自己的逻辑
//                    map.put("org_name",ConvertUtil.ConvertString(org_name));
//                    map.put("org_address",ConvertUtil.ConvertString(org_address));
//                    String[] ll= lon_lat.split(",");
//                    map.put("org_longitude",ConvertUtil.ConvertString(ll[1]));
//                    map.put("org_latitude",ConvertUtil.ConvertString(ll[0]));
//                    list.add(map);
//                }
//            }
//        }
//
//        return list;
//    }
//
//
//}
