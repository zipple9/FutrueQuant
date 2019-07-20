package com.main.service;

import com.main.domain.AccumulativeDecrease;
import com.main.domain.AccumulativeIncrease;
import com.main.domain.FutureData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/4/19
 */
@Service
@Slf4j
public class AnalysisService {


    public Map<String,Object> analyze(List<FutureData> fdList) {

        String direction;
        List<AccumulativeIncrease> aiList = new ArrayList<>();
        List<AccumulativeDecrease> adList = new ArrayList<>();

        boolean increaseFlag=false;
        boolean decreaseFlag=false;


        //遍历数据集
        for (int i = 1; i < fdList.size(); i++) {

            //标记I D
            if (fdList.get(i).getPrice().subtract(fdList.get(i - 1).getPrice()).compareTo(new BigDecimal("0")) == 1) {

                FutureData fd = fdList.get(i);
                fd.setIorD(true);
                fdList.set(i, fd);

            }
            if (fdList.get(i).getPrice().subtract(fdList.get(i - 1).getPrice()).compareTo(new BigDecimal("0")) == -1) {

                FutureData fd = fdList.get(i);
                fd.setIorD(false);
                fdList.set(i, fd);

            }
        }

        AccumulativeIncrease ai = new AccumulativeIncrease();
        AccumulativeDecrease ad = new AccumulativeDecrease();

        //再次遍历  计算增长和 下降区间
        for (int i = 2; i < fdList.size(); i++) {

            //持平
            if (fdList.get(i).getIorD() == null && fdList.get(i - 1).getIorD() == null) {
                //判断目前状态
                if(ad.getStartData() != null){
                    ad.addData(fdList.get(i - 1));
                }
                if(ai.getStartData() != null){
                    ai.addData(fdList.get(i - 1));
                }
                continue;

            }
            //上、下转平
            if(fdList.get(i).getIorD()==null ){

                if(fdList.get(i - 1).getIorD()){
                    if(ai.getStartData() != null){
                        ai.addData(fdList.get(i - 1));
                    }
                }
                if(!fdList.get(i - 1).getIorD()){
                    if(ad.getStartData() != null){
                        ad.addData(fdList.get(i - 1));
                    }
                }
                continue;
            }

            //平转上、下
            if (fdList.get(i - 1).getIorD() == null) {

                //平转上
                if(fdList.get(i).getIorD()){
                    //判断目前状态
                    if(ad.getStartData() != null){
                        ad.setEndData(fdList.get(i - 1));
                        ad.addData(fdList.get(i - 1));
                        ad.compute();
                        adList.add(ad);
                        ad = new AccumulativeDecrease();
                    }
                    if(ai.getStartData() != null){
                        ai.addData(fdList.get(i - 1));
                    }
                    //没有连增 连降的情况
                    if(ad.getStartData() == null&& ai.getStartData() == null ){
                        ai.setStartData(fdList.get(i - 1));
                    }
                }
                //平转下
                if(!fdList.get(i).getIorD()){
                    //判断目前状态
                    if(ad.getStartData() != null){
                        ad.addData(fdList.get(i - 1));
                    }
                    if(ai.getStartData() != null){
                        ai.setEndData(fdList.get(i - 1));
                        ai.addData(fdList.get(i - 1));
                        ai.compute();
                        aiList.add(ai);
                        ai = new AccumulativeIncrease();
                    }
                    //没有连增 连降的情况
                    if(ad.getStartData() == null&& ai.getStartData() == null ){
                        ad.setStartData(fdList.get(i - 1));
                    }
                }
                continue;
            }



            //连续I
            if (fdList.get(i).getIorD() && fdList.get(i - 1).getIorD()) {
                if (ai.getStartData() == null) {
                    ai.setStartData(fdList.get(i - 1));
                }
                ai.addData(fdList.get(i - 1));

            }
            //连续D
            else if (!fdList.get(i).getIorD() && !fdList.get(i - 1).getIorD()) {
                if (ad.getStartData() == null) {
                    ad.setStartData(fdList.get(i - 1));
                    increaseFlag=true;
                }
                ad.addData(fdList.get(i - 1));
            }
            //向下调头
            else if (
                    (!fdList.get(i).getIorD() && fdList.get(i - 1).getIorD())
            ) {
                if (ai.startData != null) {
                    ai.setEndData(fdList.get(i - 1));
                    ai.addData(fdList.get(i - 1));
                    //computer other fields
                    ai.compute();
                    aiList.add(ai);
                    ai = new AccumulativeIncrease();

                }

            }
            //向上调头
            else if (
                    (fdList.get(i).getIorD() && !fdList.get(i - 1).getIorD())
            ) {
                if(ad.startData !=null){
                    ad.setEndData(fdList.get(i - 1));
                    ad.addData(fdList.get(i - 1));
                    ad.compute();
                    adList.add(ad);
                    ad = new AccumulativeDecrease();

                }
            }


        }


        //return value
        Map result=new HashMap();
        result.put("aiList",aiList);
        result.put("adList",adList);
        return result;
    }

}
