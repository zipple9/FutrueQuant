package com.main.domain;

import java.sql.Timestamp;

/**
 * Created by wzy on 2019/1/19 12:03
 * 单条交易记录
 **/
public class futureData {

    private String variety;

    private String date;
    private Integer price;
    private Integer open;
    private Integer high;
    private Integer low;

    private Integer volume;
    private Integer holdings;

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setHoldings(Integer holdings) {
        this.holdings = holdings;
    }

    public String getVariety() {

        return variety;
    }

    public String getDate() {
        return date;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getOpen() {
        return open;
    }

    public Integer getHigh() {
        return high;
    }

    public Integer getLow() {
        return low;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getHoldings() {
        return holdings;
    }

    public futureData(String variety, String date, Integer open, Integer high, Integer low, Integer price, Integer volume, Integer holdings) {
        this.variety = variety;
        this.date = date;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.holdings = holdings;
    }

    public futureData(String date, Integer price, Integer open, Integer high, Integer low, Integer volume, Integer holdings) {
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
