package com.main.domain.trade;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author wzy
 * @Date 2019/7/31
 */
@Data
public class IntraDayTradeAnalysis implements TradeAnalysis {



    //区间 交易日 数量
    private Integer tradeDays=0;
    //产生交易的天数
    private Integer activeDays=0;
    //盈利天数
    private Integer winDays=0;
    //亏损天数
    private Integer loseDays=0;
    //收益率
    private BigDecimal ROE=new BigDecimal(0);

    private List paramList;

}
