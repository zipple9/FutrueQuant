package com.main.domain.trade;

import com.main.domain.FutureData;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 持仓
 * @Author wzy
 * @Date 2019/7/8
 */
@Data
public class Holding {

    //TODO  怎么处理这个字段
    private String variety;
//品种
    private String varietyName;

    private FutureData futureData;
//    //多空
//    private boolean direct;
    //数量
    private Integer count;
    //每股成本
    private BigDecimal cost;

    private BigDecimal currentValue;



    public Holding(FutureData futureData,Integer count){
        this.setCount(count);
        this.setFutureData(futureData);
        this.setCost(futureData.getPrice());
        this.setVariety(futureData.getVariety());
    }

    public Holding(){

    }


}
