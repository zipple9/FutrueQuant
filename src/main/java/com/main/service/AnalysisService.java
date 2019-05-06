package com.main.service;

import com.main.domain.AccumulativeDecrease;
import com.main.domain.AccumulativeIncrease;
import com.main.domain.FutureData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/4/19
 */
@Service
public class AnalysisService {


    private List<AccumulativeIncrease> aiList;
    private List<AccumulativeDecrease> adList;


    public void analyze(List<FutureData> fdList){

        String direction;

        //遍历数据集
        for(int i=1;i<fdList.size();i++){

            //标记I D
            if(fdList.get(i).getPrice()-fdList.get(i-1).getPrice()>0) {

                FutureData fd=fdList.get(i);
                fd.setIorD(true);
                fdList.set(i,fd);

            }
            if(fdList.get(i).getPrice()-fdList.get(i-1).getPrice()<0) {

                FutureData fd=fdList.get(i);
                fd.setIorD(false);
                fdList.set(i,fd);

            }
        }


        //再次遍历
        for(int i=1;i<fdList.size();i++){

            //连续I
            if(fdList.get(i).getIorD() && fdList.get(i-1).getIorD()){

            }
            //连续D
            else if(!fdList.get(i).getIorD() && !fdList.get(i-1).getIorD()){

            }
            //调头
            else {

            }





        }
    }

}
