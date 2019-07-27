package com.main.util;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * @Author wzy
 * @Date 2019/7/23
 */
public class BigDecimalComputeUtil {

    public static BigDecimal bdm;


    /**
     * 求两个bdm的变化率 即增长或下降百分比
     * @param newItem
     * @param oldItem
     * @return
     */
    public static Double getROC(BigDecimal newItem,BigDecimal oldItem){

        return Double.parseDouble(newItem.subtract(oldItem).divide(oldItem,8,ROUND_FLOOR).toString() );
    }






//    public static BigDecimal toBDM(Object obj){
//        this.bdm=BigDecimal
//    }
}
