package com.main.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by wzy on 2019/1/19 12:03
 * 单条交易记录
 **/
@Data
public class FutureData {

    private String variety;

    private String date;
    private BigDecimal price;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;

    private Integer volume;
    private Integer holdings;


    private Boolean IorD;


    public FutureData(String variety, String date, Integer open, Integer high, Integer low, Integer price, Integer volume, Integer holdings) {
        this.variety = variety;
        this.date = date;
        this.price = BigDecimal.valueOf((long)price);
        this.open = BigDecimal.valueOf((long)open);
        this.high = BigDecimal.valueOf((long)high);
        this.low = BigDecimal.valueOf((long)low);
        this.volume = volume;
        this.holdings = holdings;
    }

    public FutureData(String date, Integer price, Integer open, Integer high, Integer low, Integer volume, Integer holdings) {
        this.date = date;
        this.price = BigDecimal.valueOf((long)price);
        this.open = BigDecimal.valueOf((long)open);
        this.high = BigDecimal.valueOf((long)high);
        this.low = BigDecimal.valueOf((long)low);
        this.volume = volume;
        this.holdings = holdings;
    }


}
