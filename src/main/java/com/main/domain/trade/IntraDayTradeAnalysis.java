package com.main.domain.trade;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author wzy
 * @Date 2019/7/31
 */
@Data
public class IntraDayTradeAnalysis implements TradeAnalysis {



    //区间 交易日 数量
    private Integer tradeDays;
    //产生交易的天数
    private Integer activeDays;
    //盈利天数
    private Integer winDays;
    //亏损天数
    private Integer loseDays;
    //收益率
    private BigDecimal ROE;

}
