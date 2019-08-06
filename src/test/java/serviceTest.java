import com.main.baseTest;
import com.main.dao.HistoryDataDao;
import com.main.domain.AccumulativeDecrease;
import com.main.domain.AccumulativeIncrease;
import com.main.domain.FutureData;
import com.main.domain.trade.CurrentStg;
import com.main.domain.trade.Holding;
import com.main.domain.trade.Strategy;
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
    @Autowired
    private Strategy strategy;



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
//        historyDataDao.getData(20,"2018-06-15 0902","2018-06-15 0920").forEach(x->{
//            System.out.println(x);
//        });
//        historyDataDao.getData(5,"","").forEach(x->{
//            System.out.println(x);
//        });
        List<FutureData> fdList=getDataService.getData(null,"2018-06-15 0900","2018-06-15 2400");
        fdList.forEach(i-> System.out.println(i));

    }
    @Test
    public void test3(){

        List<FutureData> fdList1=getDataService.getData(null,"2018-06-15 0900","2018-06-15 2400");
        List<FutureData> fdList2=getDataService.getData(null,"2018-06-19 0900","2018-06-19 2400");
        List<FutureData> fdList3=getDataService.getData(null,"2018-06-20 0900","2018-06-20 2400");


        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(strategy.stg1(fdList1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(strategy.stg1(fdList2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(strategy.stg1(fdList3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

//        CurrentStg cs1=new CurrentStg();
//        cs1.setFdList(fdList1);
//        cs1.run();
//        CurrentStg cs2=new CurrentStg();
//        cs2.setFdList(fdList2);
//        cs2.run();
//        CurrentStg cs3=new CurrentStg();
//        cs3.setFdList(fdList3);
//        cs3.run();


    }

    @Test
    public void excute() throws Exception{
//        strategy.runStrategy();
        strategy.runStrategyCct();
    }

}
