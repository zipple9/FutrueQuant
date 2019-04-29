package com.main.domain;

import lombok.Data;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/04/26
 */
@Data
public class AccumulativeDecrease {


    private Integer duringDays;
    private Double decreaseValue;
    private Double decreasePercentage;

}
