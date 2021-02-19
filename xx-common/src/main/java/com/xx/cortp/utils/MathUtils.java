package com.xx.cortp.utils;

import java.math.BigDecimal;

/** 
*  浮点数操作，加减乘除，保证浮点数精度不缺失
*/
public class MathUtils {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算
     * 
     * @param v1
     * @param v2
     * @return double
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * @param v1 :减数
     * @param v2 :被减数
     * @return double
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * @param v1 
     * @param v2
     * @return double
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /** 
    *  提供精确的除法运算(默认精度为精确到小数点后10位)
    * @param v1 :除数
    * @param v2 :被除数
    * @return double     
    */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /** 
    *  提供精确的除法运算
    * @param v1    :除数
    * @param v2 :被除数
    * @param scale :精度
    * @return double     
    */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /** 
    *  提供精确的四舍五入运算
    * @param v :浮点数
    * @param scale :精度
    * @return double     
    */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
}
