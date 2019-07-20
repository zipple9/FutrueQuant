import com.main.baseTest;
import com.main.dao.HistoryDataDao;
import com.main.domain.AccumulativeDecrease;
import com.main.domain.AccumulativeIncrease;
import com.main.domain.FutureData;
import com.main.service.AnalysisService;
import com.main.service.myService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.reflect.misc.FieldUtil;

import java.util.List;
import java.util.Map;

public class serviceTest extends baseTest {


    @Autowired
    private com.main.service.rawFileHandler rawFileHandler;
    @Autowired
    private com.main.service.myService myService;
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private HistoryDataDao historyDataDao;

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
        historyDataDao.getData(20,"2018/06/15 0902","2018/06/15 0920").forEach(x->{
            System.out.println(x);
        });
        historyDataDao.getData(5,"","").forEach(x->{
            System.out.println(x);
        });

    }
    @Test
    public void test3(){
        historyDataDao.getHistoryData().forEach(x->{
            System.out.println(x);
        });
    }

}
