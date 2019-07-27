import com.main.baseTest;
import com.main.dao.HistoryDataDao;
import com.main.domain.AccumulativeDecrease;
import com.main.domain.AccumulativeIncrease;
import com.main.domain.FutureData;
import com.main.domain.trade.Holding;
import com.main.domain.trade.Trader;
import com.main.service.AnalysisService;
import com.main.service.GetDataService;
import com.main.service.myService;
import com.main.util.BigDecimalComputeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.misc.FieldUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class serviceTest extends baseTest {


    @Autowired
    private com.main.service.rawFileHandler rawFileHandler;
    @Autowired
    private com.main.service.myService myService;
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private HistoryDataDao historyDataDao;
    @Autowired
    private GetDataService getDataService;
    @Test
    public void mainTest(){
//        System.out.println(myService.getDailyData());
        Map result=analysisService.analyze(myService.getHistoryData());
        List<AccumulativeIncrease> aiList=(List)result.get("aiList");
        List<AccumulativeDecrease> adList=(List)result.get("adList");

        aiList.forEach(i->{
            System.out.println(i.startDate+"--"+i.endDate+":  "+i.getIncreasePercentage()+"  "+i.getIncreaseValue());;
        });
        adList.forEach(i->{
            System.out.println(i.startDate+"--"+i.endDate+":  "+i.getDecreasePercentage()+"  "+i.getDecreaseValue());;
        });

    }

    @Test
    public void test2(){
        historyDataDao.getData(20,"2018-06-15 0902","2018-06-15 0920").forEach(x->{
            System.out.println(x);
        });
        historyDataDao.getData(5,"","").forEach(x->{
            System.out.println(x);
        });

    }
    @Test
    public void test3(){
        System.out.println(BigDecimalComputeUtil.getROC(new BigDecimal("15"),new BigDecimal("10")));
    }

    @Test
    public void excute() throws Exception{

        boolean buyFlag=true;

        Trader trader=new Trader();
        trader.setMoney(new BigDecimal("50000"));

        List<FutureData> fdList=getDataService.getData(2000,"","");

        for(int i=2;i<fdList.size();i++){

            //连续三个 量价齐增
            if( buyFlag &&
                    fdList.get(i).getPrice().compareTo(fdList.get(i-1).getPrice())==1 && fdList.get(i-1).getPrice().compareTo(fdList.get(i-2).getPrice())==1 && fdList.get(i).getVolume()>fdList.get(i-1).getVolume() && fdList.get(i-1).getVolume()>fdList.get(i-2).getVolume()){
                //bug long
                Holding holding=new Holding(fdList.get(i),2);
                buyFlag=trader.trade(1,holding);
//                log.info(trader.toString());
//                log.info(trader.getMoney().toString());
            }

            //2%涨幅  卖出
            Optional op=Optional.ofNullable(trader.getLongHoldings().get("rmb"));
            if( op.isPresent()){
//                log.info("涨幅判断"+BigDecimalComputeUtil.getROC(fdList.get(i).getPrice(),trader.getLongHoldings().get("rmb").getCost()));
            }
            if(  op.isPresent() && BigDecimalComputeUtil.getROC(fdList.get(i).getPrice(),trader.getLongHoldings().get("rmb").getCost()) >0.005  ){

                trader.getLongHoldings().get("rmb").setFutureData(fdList.get(i));

                //全部卖出
                trader.trade(2,trader.getLongHoldings().get("rmb"));
            }

        }


        //结果展示
        System.out.println(trader);
    }

}
