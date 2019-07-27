package com.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.main.domain.FutureData;
import com.main.service.AnalysisService;
import com.main.service.GetDataService;
import com.main.service.myService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/data")
public class DataController {

@Autowired
private myService myService;
@Autowired
private GetDataService getDataService;
@Autowired
private AnalysisService analysisService;





    @GetMapping("/get")
    @ResponseBody
    public JSONObject getHistoryData(HttpServletRequest request){



        Integer limit=request.getParameter("limit")==null?500:Integer.parseInt(request.getParameter("limit"));
        String startDate=request.getParameter("startDate")==null?"":request.getParameter("startDate");
        String endDate=request.getParameter("endDate")==null?"":request.getParameter("endDate");


        JSONObject result=new JSONObject();
        List<FutureData> dataList=getDataService.getData(limit,startDate,endDate);


        result.put("dataList",dataList);
        result.put("analyze",analysisService.analyze(dataList));
        return result;
    }

}
