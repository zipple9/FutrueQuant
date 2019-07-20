package com.main.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/05/06
 */
@Data
public class Period {

    public Integer duringDays;

    public String startDate;
    public String endDate;

    public Double valueChange;

    public FutureData startData;
    public FutureData endData;

    public List<FutureData> dataList=new ArrayList<>();

    public void reSet(){

    }



    public void addData(FutureData futureData){
        this.dataList.add(futureData);
    }

}
