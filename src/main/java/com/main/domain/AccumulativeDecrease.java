package com.main.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/04/26
 */
@Data
public class AccumulativeDecrease extends Period{


    private Double decreaseValue;
    private Double decreasePercentage;

}
