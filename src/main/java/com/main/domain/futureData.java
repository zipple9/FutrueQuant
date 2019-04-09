package com.main.domain;

import java.sql.Timestamp;

/**
 * Created by wzy on 2019/1/19 12:03
 **/
public class futureData {

    private String variety;

    private String date;
    private int price;
    private int open;
    private int high;
    private int low;

    private int volume;
    private int holdings;

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setHoldings(int holdings) {
        this.holdings = holdings;
    }

    public String getVariety() {

        return variety;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public int getOpen() {
        return open;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public int getVolume() {
        return volume;
    }

    public int getHoldings() {
        return holdings;
    }

    public futureData(String variety, String date, int open, int high, int low, int price, int holdings, int volume) {
        this.variety = variety;
        this.date = date;
        this.price = price;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.holdings = holdings;
    }
}
