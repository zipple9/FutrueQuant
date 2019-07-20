package com.main.domain;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class tradeLog {

    private String trader;
    private String traderVariety;
    private String tradeDate;
    private Integer tradeVolume;
    private BigDecimal tradePrice;

}
