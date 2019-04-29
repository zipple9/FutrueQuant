package com.main.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by wzy on 2019/1/19 12:03
 * 单条交易记录
 **/
@Data
public class FutureData {

    private String variety;

    private String date;
    private Integer price;
    private Integer open;
    private Integer high;
    private Integer low;

    private Integer volume;
    private Integer holdings;


    private Boolean IorD;


    public FutureData(String variety, String date, Integer open, Integer high, Integer low, Integer price, Integer volume, Integer holdings) {
        this.variety = variety;
        this.date = date;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.holdings = holdings;
    }

    public FutureData(String date, Integer price, Integer open, Integer high, Integer low, Integer volume, Integer holdings) {
        this.date = date;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.holdings = holdings;
    }


    @Override
    public String toString() {
        return this.getVariety()+" "+this.getDate()+" "+this.getPrice()+" "+this.getOpen()+" "+this.getHigh()+" "+this.getLow()+" "+this.getVolume()+" "+this.getHoldings();
    }
}
