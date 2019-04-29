import com.main.baseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class serviceTest extends baseTest {


    @Autowired
    private com.main.service.rawFileHandler rawFileHandler;
    @Autowired
    private com.main.service.myService myService;

    @Test
    public void mainTest(){
        rawFileHandler.wiriteDB();
//        List<FutureData> fdList=new ArrayList<>();
//        fdList.add(new FutureData("20190102",2000,2000,3000,3000,0,0));
//        myService.addHistroyData(fdList);
    }

}
