package com.liuxiaolan.internalcommon.util;

import java.math.BigDecimal;

public class BigDecimalUtils {

    /** 方法描述 加法
    * @return 
    * @author liuxiaolan
    * @date 2023/4/26
    */
    public static double add(double v1,double v2){
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).doubleValue();
    }
    
    /** 方法描述 除法
    * @return 
    * @author liuxiaolan
    * @date 2023/4/26
    */
    public static double divide(int v1,int v2){
        if(v2 <= 0){
            throw new RuntimeException("除数非法");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /** 方法描述 减法
    * @return
    * @author liuxiaolan
    * @date 2023/4/26
    */
    public static double subtract(double v1,double v2){
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }


    /** 方法描述 乘法
    * @return
    * @author liuxiaolan
    * @date 2023/4/26
    */
    public static double multiply(double v1,double v2){
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).doubleValue();
    }








}
