package com.main.domain.trade;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author wzy
 * @Date 2019/7/4
 */
@Data
public class Trader {

private String traderNo;

    private BigDecimal money;

    private Map<String,Holding> longHoldings;

    private Map<String,Holding> shortHoldings;




    public void trade(int tradeType,Holding holding) throws Exception{

        String varietyCode=holding.getVariety();
        Holding longHolding= longHoldings.get(varietyCode);
        Holding shortHolding= shortHoldings.get(varietyCode);
        switch (tradeType){
            //buy long
            case 1:
                longHolding=(mergeHolding(longHolding,holding));
                this.longHoldings.put(varietyCode,longHolding);

                this.money=this.money.subtract(holding.getCost());
                break;
            //sell long
            case 2:
                shortHolding=(mergeHolding(shortHolding,holding));
                this.shortHoldings.put(varietyCode,shortHolding);

                this.money=this.money.add(holding.getCost());
                break;
        }


    }

    /**
     * 合并两个holding的 仓位
     * cost重新计算
     * @param oldHolding
     * @param newHolding
     * @return
     */
    public Holding mergeHolding(Holding oldHolding,Holding newHolding) throws Exception{

        if(!oldHolding.getVariety().equals(newHolding.getVariety())){
            throw new Exception("holding品种不同");
        }

        Holding resultHolding=new Holding();
        //计算新的持仓数
        resultHolding.setCount(oldHolding.getCount()+newHolding.getCount());
        //计算新的成本
        resultHolding.setCost(
                oldHolding.getCost().multiply(new BigDecimal(oldHolding.getCount()))
                        .add(newHolding.getCost().multiply(new BigDecimal(newHolding.getCount())))
                .divide(new BigDecimal(oldHolding.getCount()+newHolding.getCount()))
        );
        return  resultHolding;

    }
}
