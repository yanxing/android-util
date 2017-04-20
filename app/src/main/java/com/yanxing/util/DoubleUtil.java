package com.yanxing.util;

import java.math.BigDecimal;

/**
 * double数值精度问题
 * Created by 李双祥 on 2017/4/18.
 */
public class DoubleUtil {
    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的加法运算
     */
    public static double add(double v0,double v1, double v2,double v3,double v4,double v5,double v6,double v7)
    {
        BigDecimal b0 = new BigDecimal(Double.toString(v0));
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal b3 = new BigDecimal(Double.toString(v3));
        BigDecimal b4 = new BigDecimal(Double.toString(v4));
        BigDecimal b5 = new BigDecimal(Double.toString(v5));
        BigDecimal b6 = new BigDecimal(Double.toString(v6));
        BigDecimal b7 = new BigDecimal(Double.toString(v7));
        return b0.add(b1).add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
