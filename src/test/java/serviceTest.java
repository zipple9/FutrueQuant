import com.main.BaseTest;
import com.main.dao.HistoryDataDao;
import com.main.domain.AccumulativeDecrease;
import com.main.domain.AccumulativeIncrease;
import com.main.domain.trade.Strategy;
import com.main.service.AnalysisService;
import com.main.service.GetDataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
public class serviceTest extends BaseTest {


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
    @Autowired
    private com.main.service.testService testService;


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
    @Transactional
    public void test2(){
        testService.deleteData();
//        testService.addData();
        testService.selectData();
    }

    @Test
    public void excute() throws Exception{
//        strategy.runStrategy();
//        strategy.runStartegyNoTPAll();

//        strategy.runStartegyNoTP();
//
        strategy.runStartegyTP();
    }

}
