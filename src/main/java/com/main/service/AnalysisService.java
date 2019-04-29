package com.main.service;

import com.main.domain.AccumulativeDecrease;
import com.main.domain.AccumulativeIncrease;
import com.main.domain.FutureData;

import java.util.List;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/4/19
 */
public class AnalysisService {


    private List<AccumulativeIncrease> accumulativeIncrease;
    private AccumulativeDecrease accumulativeDecrease;


    public void analyze(List<FutureData> fdList){

        String direction;

        //遍历数据集
        for(int i=1;i<fdList.size();i++){

            if(fdList.get(i).getPrice()-fdList.get(i-1).getPrice()>0) {
                FutureData fd=fdList.get(i);
                fd.setIorD(true);
                fdList.set(i,fd);

            }

            if(fdList.get(i).getPrice()-fdList.get(i-1).getPrice()<0) {


            }



        }
    }

}
