import com.main.baseTest;
import com.main.service.AnalysisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class serviceTest extends baseTest {


    @Autowired
    private com.main.service.rawFileHandler rawFileHandler;
    @Autowired
    private com.main.service.myService myService;
    @Autowired
    private AnalysisService analysisService;

    @Test
    public void mainTest(){
//        analysisService.analyze();

    }

}
