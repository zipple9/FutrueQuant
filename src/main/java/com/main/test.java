package com.main;

import com.main.util.DailyDataUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by wzy on 2019/1/17 16:44
 **/
@Component
public class test {

    public static void main (String args[]){
        String str=new DailyDataUtil().getData();
        str=str.substring(19,146);
        System.out.println(str);
        str=str.substring(0,8)+str.substring(117)+" "+str.substring(8,38)+str.substring(62,71)+str.substring(95,111);
        System.out.println(str);
    }


}
