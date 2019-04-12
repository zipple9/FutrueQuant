import com.main.baseTest;
import com.main.domain.futureData;
import com.main.service.myService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class serviceTest extends baseTest {


    @Autowired
    private com.main.service.rawFileHandler rawFileHandler;
    @Autowired
    private com.main.service.myService myService;

    @Test
    public void mainTest(){
        rawFileHandler.wiriteDB();
//        List<futureData> fdList=new ArrayList<>();
//        fdList.add(new futureData("20190102",2000,2000,3000,3000,0,0));
//        myService.addHistroyData(fdList);
    }

}
