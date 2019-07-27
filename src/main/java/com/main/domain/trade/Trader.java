package com.main.domain.trade;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * @Author wzy
 * @Date 2019/7/4
 */
@Data
@Slf4j
public class Trader {

private String traderNo;

    private BigDecimal money;

    private Map<String,Holding> longHoldings=new HashMap<>();

    private Map<String,Holding> shortHoldings=new HashMap<>();




    public Boolean trade(int tradeType,Holding holding) throws Exception{

        String varietyCode=holding.getVariety();
        Holding longHolding= longHoldings.get(varietyCode);
        Holding shortHolding= shortHoldings.get(varietyCode);
        switch (tradeType){
            //buy long
            case 1:
                //判断资金是否足够买入
                if(this.money.subtract(holding.getCost().multiply(new BigDecimal(holding.getCount()) )).compareTo(BigDecimal.ZERO)==1){
                    longHolding=(mergeHolding(longHolding,holding,1));
                    this.longHoldings.put(varietyCode,longHolding);
                    //计算剩余资金
                    this.money=this.money.subtract(holding.getCost().multiply(new BigDecimal(holding.getCount()) ));
                    log.info("买多 ："+holding.getFutureData().toString()+holding.getCount());
                    return true;
                }else{
                    log.info("买多 ："+holding.getFutureData().toString()+"资金不足");
                    return false;
                }
            //sell long
            case 2:
                //如果空仓 或者 卖出量大于 买入量     退出sl交易
                if(longHolding==null || holding.getCount()>longHolding.getCount()){
                    log.info("卖多 交易失败");
                    return false;
                }
                longHolding=(mergeHolding(longHolding,holding,2));
                this.longHoldings.put(varietyCode,longHolding);

                this.money=this.money.add(holding.getFutureData().getPrice().multiply(new BigDecimal(holding.getCount())));
                log.info("卖多 ："+holding.getFutureData().toString()+holding.getCount());
                return true;
        }

        return null;
    }

    /**
     * 合并两个holding的 仓位
     * cost重新计算
     * @param oldHolding
     * @param newHolding
     * @return
     */
    private Holding mergeHolding(Holding oldHolding,Holding newHolding,int BS) throws Exception{

        Holding resultHolding=new Holding();
        switch (BS){
            //buy
            case 1:
                //判断原holding 是否为空
                if(!Optional.ofNullable(oldHolding).isPresent()){
                    return newHolding;
                }

                if(!oldHolding.getVariety().equals(newHolding.getVariety())){
                    throw new Exception("holding品种不同");
                }

                //计算新的持仓数
                resultHolding.setCount(oldHolding.getCount()+newHolding.getCount());
                //计算新的成本
                resultHolding.setCost(
                        oldHolding.getCost().multiply(new BigDecimal(oldHolding.getCount()))
                                .add(newHolding.getCost().multiply(new BigDecimal(newHolding.getCount())))
                                .divide(new BigDecimal(oldHolding.getCount()+newHolding.getCount()),8,ROUND_FLOOR)
                );
                resultHolding.setVariety(newHolding.getVariety());
                return  resultHolding;
            //sell
            case 2:
                resultHolding.setCount(oldHolding.getCount()-newHolding.getCount());



        }
        return null;
    }
}
