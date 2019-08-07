package com.main.domain.trade;

import com.alibaba.fastjson.JSONObject;
import com.main.domain.FutureData;
import com.main.service.GetDataService;
import com.main.util.BigDecimalComputeUtil;
import com.main.util.MyDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

/**
 * @Author wzy
 * @Date 2019/7/22
 */
@Slf4j
@Component
public class Strategy {
    @Autowired
    private GetDataService getDataService;


    public JSONObject runStrategy() {

        try {
            List<Trader> traderList = new ArrayList<>();

            List<LocalDate> localDateList = MyDateUtil.getEveryDay("20180615", "20190301");
            for (int i = 0; i < localDateList.size(); i++) {
                traderList.add(this.stg1(getDataService.getData(null, localDateList.get(i) + " 0900", localDateList.get(i) + " 2400")));
            }

//            traderList.forEach(i-> System.out.println(i.getMoney()));
            analyzeTrade(traderList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多线程 处理
     *
     * @return
     */
    public JSONObject runStrategyCct() {
        log.info("cct");

        try {
            List<Trader> traderList = new Vector<>();


            LocalDateTime timeStart=LocalDateTime.now();

            Runnable producer = new Runnable() {

                List<LocalDate> localDateList = MyDateUtil.getEveryDay("20180615", "20190301");
                Integer count = 0;
                Object lock = new Object();

                @Override
                public void run() {

                    while (true) {
                        synchronized (lock) {
                            if (count < localDateList.size()) {
//                                List<FutureData> fdList = getDataService.getData(null, localDateList.get(count) + " 0900", localDateList.get(count) + " 2400");
//                                try {
//                                    Thread.sleep(20);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                                try {
//                                    traderList.add(stg1(fdList));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                count++;
                                System.out.println(Thread.currentThread().getName() + "    ----" + count);
//                                log.info(count.toString());
                            }
                        }
                    }
                }
            };

            Runnable consumer = new Runnable() {
                @Override
                public void run() {

                }
            };

            Thread t1 = new Thread(producer);
            Thread t2 = new Thread(producer);
            t1.setName("t1");
            t2.setName("t2");
            t1.start();
            t2.start();

//            traderList.forEach(i-> System.out.println(i.getMoney()));


            while(!t1.isAlive() && !t2.isAlive()){
                System.out.println(traderList);

                LocalDateTime timeEnd = LocalDateTime.now();
                log.info(Duration.between(timeStart,timeEnd).toMillis()+"--------------------");
            }

            System.out.println("------------end");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String args[]) {
        new Strategy().runStrategyCct();
    }


    public void analyzeTrade(List<Trader> traders) {
        IntraDayTradeAnalysis idTradeAnalysis = new IntraDayTradeAnalysis();
        idTradeAnalysis.setTradeDays(0);
        idTradeAnalysis.setActiveDays(0);
        idTradeAnalysis.setWinDays(0);
        idTradeAnalysis.setLoseDays(0);
        idTradeAnalysis.setROE(new BigDecimal(0));

        for (int i = 0; i < traders.size(); i++) {
            if (traders.get(i) != null) {
                idTradeAnalysis.setTradeDays(idTradeAnalysis.getTradeDays() + 1);


                if (traders.get(i).getMoney().compareTo(new BigDecimal(50000)) == 0) {
                    idTradeAnalysis.setActiveDays(idTradeAnalysis.getActiveDays() + 1);
                }
                if (traders.get(i).getMoney().compareTo(new BigDecimal(50000)) == 1) {
                    idTradeAnalysis.setWinDays(idTradeAnalysis.getWinDays() + 1);
                }
                if (traders.get(i).getMoney().compareTo(new BigDecimal(50000)) == -1) {
                    idTradeAnalysis.setLoseDays(idTradeAnalysis.getLoseDays() + 1);
                }
                idTradeAnalysis.setROE(idTradeAnalysis.getROE().add(traders.get(i).getMoney()));


            }

        }

        idTradeAnalysis.setROE(idTradeAnalysis.getROE().divide(new BigDecimal(idTradeAnalysis.getTradeDays() * 50000), 6, BigDecimal.ROUND_FLOOR));

        System.out.println(idTradeAnalysis);
    }


    public Trader stg1(List<FutureData> fdList) throws Exception {
        boolean buyFlag = true;

        Trader trader = new Trader();
        trader.setMoney(new BigDecimal("50000"));

//        List<FutureData> fdList=getDataService.getData(5000,"","");
//        Optional op=Optional.ofNullable(trader.getLongHoldings().get("rmb"));
        for (int i = 2; i < fdList.size(); i++) {


            //每日清仓
            //邻近交易日结束，开始清仓
            if (trader.getLongHoldings().get("rmb") != null && i == fdList.size() - 3) {
                trader.getLongHoldings().get("rmb").setFutureData(fdList.get(i));
                trader.trade(2, trader.getLongHoldings().get("rmb"));
                break;
            }

            //达到  涨幅  卖出
            if (trader.getLongHoldings().get("rmb") != null && BigDecimalComputeUtil.substractValue(fdList.get(i).getPrice(), trader.getLongHoldings().get("rmb").getCost(), 5.0)) {

//                log.info("sell");
                trader.getLongHoldings().get("rmb").setFutureData(fdList.get(i));

                //全部卖出
                trader.trade(2, trader.getLongHoldings().get("rmb"));
            }
            //下跌 止损
            else if (trader.getLongHoldings().get("rmb") != null && BigDecimalComputeUtil.substractValue(trader.getLongHoldings().get("rmb").getCost(), fdList.get(i).getPrice(), 2.0)) {

            }

            //连续三个 量价齐增
            else if (buyFlag &&
                    fdList.get(i).getPrice().compareTo(fdList.get(i - 1).getPrice()) == 1 && fdList.get(i - 1).getPrice().compareTo(fdList.get(i - 2).getPrice()) == 1 && fdList.get(i).getVolume() > fdList.get(i - 1).getVolume() && fdList.get(i - 1).getVolume() > fdList.get(i - 2).getVolume()) {
                //bug long
                Holding holding = new Holding(fdList.get(i), 10);
                buyFlag = trader.trade(1, holding);
//                log.info(trader.toString());
                log.info(trader.getMoney().toString());
                log.info(trader.getLongHoldings().get("rmb").getCost().toString());

            }


        }


        //结果展示
//        System.out.println(trader);
        return trader;
    }
}
