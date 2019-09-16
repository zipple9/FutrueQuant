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
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static com.main.constant.dataMemory;

/**
 * @Author wzy
 * @Date 2019/7/22
 */
@Slf4j
@Component
public class Strategy {
    @Autowired
    private GetDataService getDataService;

    //创建线程池
    @Autowired
    private ExecutorService executorService=Executors.newFixedThreadPool(20);

    //    static List<Object> result = new Vector<>();
    static List<FutureData> result = new ArrayList<>();


    /**
     * 不使用线程池 执行策略
     * @return
     */
    public JSONObject runStrategy() {

        LocalDateTime startTime = LocalDateTime.now();

        Map<String, List> dateMap =getData("2018-06-15", "2019-03-01");

        List<TradeAnalysis> analysisList=new ArrayList<>();


        List<Trader> traderList = new ArrayList<>();




        double sellPoint=1.0;
        double sellPoint_s=1.0;
        double sellPoint_e=10.0;

        double stoplossPoint=1.0;
        double stoplossPoint_s=1.0;
        double stoplossPoint_e=10.0;

        while (sellPoint<sellPoint_e){

            while (stoplossPoint<stoplossPoint_e){
                //对处理好的数据 dateMap 套用策略
                Iterator it=dateMap.keySet().iterator();
                List<Trader> traderListParam = new ArrayList<>();

                while(it.hasNext()){
                    try {
                        traderListParam.add(stg1(dateMap.get(it.next()),sellPoint,stoplossPoint));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                List<Double> paramList=new ArrayList<>();
                paramList.add(sellPoint);
                paramList.add(stoplossPoint);
                analysisList.add(analyzeTrade(traderListParam,paramList));

                stoplossPoint+=0.3;
            }
            sellPoint+=0.3;
            stoplossPoint=stoplossPoint_s;
        }

        LocalDateTime endTime= LocalDateTime.now();
        System.out.println(" strategy    over   completed in " + Duration.between(startTime, endTime).toMillis() + " ms");
        System.out.println(analysisList.size());
        return null;
    }


    /**
     * 线程池策略
     * @return
     */
    public JSONObject runStartegyTP() {


        LocalDateTime startTime = LocalDateTime.now();

        Map<String, List> dateMap =getData("2018-06-15", "2019-03-01");

        List<TradeAnalysis> analysisList=new Vector<>();


        List<Trader> traderList = new Vector<>();

        //callable 内部类
        class ThreadCall implements Callable {
            private Map paramMap;

            public void setParamMap(Map paramMap) {
                this.paramMap = paramMap;
            }

            @Override
            public List<Trader> call() throws Exception {
                LocalDateTime start = LocalDateTime.now();

                double sellPoint=(double)paramMap.get("p1");
                double stoplossPoint=(double)paramMap.get("p2");

//                System.out.println(sellPoint+"   "+stoplossPoint);
                Iterator it=dateMap.keySet().iterator();

                //一种参数配置的 全部traders
                List<Trader> traderListParam=new ArrayList<>();
                while (it.hasNext()){
                    traderListParam.add(stg1(dateMap.get(it.next()),sellPoint,stoplossPoint   ));
                }




                List<Double> paramList=new ArrayList<>();
                paramList.add(sellPoint);
                paramList.add(stoplossPoint);
                analysisList.add(analyzeTrade(traderListParam,paramList));

                LocalDateTime end = LocalDateTime.now();

//                System.out.println("多线程---日期：" + date + "   completed in " + Duration.between(start, end).toMillis());
                return traderListParam;
            }
        }



        double sellPoint=1.0;
        double sellPoint_s=1.0;
        double sellPoint_e=10.0;

        double stoplossPoint=1.0;
        double stoplossPoint_s=1.0;
        double stoplossPoint_e=10.0;

        while (sellPoint<sellPoint_e){

            while (stoplossPoint<stoplossPoint_e){
                //对处理好的数据 dateMap 套用策略
                Iterator it=dateMap.keySet().iterator();


                Map<String,Object> paramMap=new HashMap<>();
                paramMap.put("fdList",dateMap.get(it.next()));
                paramMap.put("p1",sellPoint);
                paramMap.put("p2",stoplossPoint);
                ThreadCall threadCall=new ThreadCall();
                threadCall.setParamMap(paramMap);
//                log.info("before executor  ----"+paramMap.get("p1")+"    "+paramMap.get("p2"));
                executorService.submit(threadCall);


//                while(it.hasNext()){
//                    try {
////                        traderList.add(stg1(dateMap.get(it.next()),sellPoint,stoplossPoint));
//                        Map<String,Object> paramMap=new HashMap<>();
//                        paramMap.put("fdList",dateMap.get(it.next()));
//                        paramMap.put("p1",sellPoint);
//                        paramMap.put("p2",stoplossPoint);
//                        ThreadCall threadCall=new ThreadCall();
//                        threadCall.setParamMap(paramMap);
//                        executorService.submit(threadCall);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                stoplossPoint+=0.3;
            }
            sellPoint+=0.3;
            stoplossPoint=stoplossPoint_s;
        }
        executorService.shutdown();
        while (true){
            if(executorService.isTerminated()){
                LocalDateTime endTime= LocalDateTime.now();
                System.out.println("TP strategy    over   completed in " + Duration.between(startTime, endTime).toMillis() + " ms");
                System.out.println(analysisList.size());
                return null;
            }
        }
    }


    public JSONObject runStartegyNoTPAll() {
        LocalDateTime startTime = LocalDateTime.now();
        List<FutureData> dataList = getDataService.getData(null, "2018-06-15 0900", "2019-03-01 2400");

        System.out.println("dataList    " + dataList.size());
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("All strategy    over   completed in " + Duration.between(startTime, endTime).toMillis() + " ms");

        return null;


    }


    /**
     * 获取数据
     * @param
     */
    public Map getData(String startDate,String endDate){
        LocalDateTime startTime = LocalDateTime.now();

        Map<String, List> dataMap = new HashMap<>();
        List<FutureData> dataList = new ArrayList<>();
        // 如果dataMemory中 有数据 则直接查询 不经过db
        if(dataMemory.containsKey(startDate+","+endDate+":M")){
            dataMap=(Map)dataMemory.get(startDate+","+endDate+":M");
            dataList=(List)dataMemory.get(startDate+","+endDate+":R");
        }
        else {
            dataList=getDataService.getData(null, startDate+" 0900", endDate+" 2400");

            for (int i = 0; i < dataList.size(); i++) {

                List<FutureData> temp = new ArrayList<>();
                temp.add(dataList.get(i));


                if (dataMap.get(dataList.get(i).getDate().substring(0, 10)) == null) {
                    dataMap.put(dataList.get(i).getDate().substring(0, 10), temp);
                } else {
                    temp.addAll(dataMap.get(dataList.get(i).getDate().substring(0, 10)));
                    dataMap.put(dataList.get(i).getDate().substring(0, 10), temp);
                }

            }

            dataMemory.put(startDate+","+endDate+":M",dataMap);
            dataMemory.put(startDate+","+endDate+":R",dataList);

        }

        LocalDateTime endTime = LocalDateTime.now();
        System.out.println("get data: "+dataList.size()+"条  共计"+dataMap.size()+"天   completed in " + Duration.between(startTime, endTime).toMillis() + " ms");

        return dataMap;

    }

    public static void main(String args[]) {
//        new Strategy().runStrategyCct();


    }

    /**
     * 对策略跑出的结果 List<Trader> 进行分析汇总
     * @param traders
     */
    public IntraDayTradeAnalysis analyzeTrade(List<Trader> traders,List paramList) {
        IntraDayTradeAnalysis idTradeAnalysis = new IntraDayTradeAnalysis();
//        idTradeAnalysis.setTradeDays(0);
//        idTradeAnalysis.setActiveDays(0);
//        idTradeAnalysis.setWinDays(0);
//        idTradeAnalysis.setLoseDays(0);
//        idTradeAnalysis.setROE(new BigDecimal(0));
        idTradeAnalysis.setParamList(paramList);

        for (int i = 0; i < traders.size(); i++) {
            if (traders.get(i) != null) {
                idTradeAnalysis.setTradeDays(idTradeAnalysis.getTradeDays() + 1);


                if (traders.get(i).getMoney().compareTo(new BigDecimal(50000)) != 0) {
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
        return idTradeAnalysis;
    }

    /**
     * 策略1
     * @param fdList
     * @return
     * @throws Exception
     */
    public Trader stg1(List<FutureData> fdList,double sellPoint,double stoplossPoint) throws Exception {
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
            if (trader.getLongHoldings().get("rmb") != null && BigDecimalComputeUtil.substractValue(fdList.get(i).getPrice(), trader.getLongHoldings().get("rmb").getCost(), sellPoint)) {

//                log.info("sell");
                trader.getLongHoldings().get("rmb").setFutureData(fdList.get(i));

                //全部卖出
                trader.trade(2, trader.getLongHoldings().get("rmb"));
            }
            //下跌 止损
            else if (trader.getLongHoldings().get("rmb") != null && BigDecimalComputeUtil.substractValue(trader.getLongHoldings().get("rmb").getCost(), fdList.get(i).getPrice(), stoplossPoint)) {

            }

            //连续三个 量价齐增
            else if (buyFlag &&
                    fdList.get(i).getPrice().compareTo(fdList.get(i - 1).getPrice()) == 1 && fdList.get(i - 1).getPrice().compareTo(fdList.get(i - 2).getPrice()) == 1 && fdList.get(i).getVolume() > fdList.get(i - 1).getVolume() && fdList.get(i - 1).getVolume() > fdList.get(i - 2).getVolume()) {
                //bug long
                Holding holding = new Holding(fdList.get(i), 10);
                buyFlag = trader.trade(1, holding);
//                log.info(trader.toString());
                log.debug(trader.getMoney().toString());
                log.debug(trader.getLongHoldings().get("rmb").getCost().toString());

            }


        }


        //结果展示
//        System.out.println(trader);
        return trader;
    }
}
