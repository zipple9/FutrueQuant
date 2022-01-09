package com.aticlecollect.spyder;


import com.aticlecollect.entity.DocEntity;
import com.commonbase.util.CommonUtils;
import lombok.Data;
import org.joda.time.DateTime;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/9/7-22:34
 */
@Data
public class EastMoneyPipeline implements Pipeline {

    private List<DocEntity> docs =new ArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {


        DocEntity docEntity = resultItems.get("data");

        docs.add(docEntity);
    }
}
