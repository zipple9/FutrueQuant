package com.main.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/04/26
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AccumulativeDecrease extends Period{

    private BigDecimal decreaseValue;
    private BigDecimal decreasePercentage;
    /**
     * compute other fields according to startData and endData
     */
    public void compute(){
        this.duringDays=this.dataList.size();

        if(this.endData !=null && this.startData !=null) {
            this.decreaseValue =this.endData.getPrice().subtract(this.startData.getPrice());
            this.decreasePercentage =this.endData.getPrice().subtract(this.startData.getPrice()).divide(this.startData.getPrice(),8,ROUND_FLOOR);
            this.startDate = this.startData.getDate();
            this.endDate = this.endData.getDate();
        }

    }

}
