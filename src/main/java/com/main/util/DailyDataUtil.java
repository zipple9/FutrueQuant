package com.main.util;

import com.main.domain.futureData;
import com.main.service.myService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by wzy on 2019/1/17 22:34
 **/
@Component
public class DailyDataUtil {

    @Autowired
    private myService ms;

    private String dataFile="C:\\Users\\Administrator\\Desktop\\future_data\\dailyData.txt";


    /**
     * 新浪财经接口取到的数据 volume 是递增的 不是每分钟的量
     */

    public String getData(){

        String urlString="http://hq.sinajs.cn/list=RB1905";
//        String urlString="https://www.baidu.com/";
        String result="";

        try{
            URL url=new URL(urlString);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream reader=connection.getInputStream();
            int i=0;
            byte[] b=new byte[1024];
            while((i=reader.read(b))!=-1){
                result=new String(b,"gbk");
                byte[] zero=new byte[]{0};

                //把byte[]里面多余的0处理掉
                result=result.replaceAll(new String(new byte[]{0},"gbk"),"") ;
                //把编码方式改为utf8
                result=new String(result.getBytes(),"utf-8");
            }

            // 将原始字符串 输入db
            ms.addRawData(result);

            String[] sa=processData(result);
            System.out.println(Arrays.toString(sa));

            futureData fd=new futureData(sa[0],sa[1],Integer.parseInt(sa[2]),Integer.parseInt(sa[3]),Integer.parseInt(sa[4]),Integer.parseInt(sa[5]),Integer.parseInt(sa[6]),Integer.parseInt(sa[7]));

            //将处理过的数据 存入db
            try{
                ms.addData_M(fd);

            }catch (Exception e){e.printStackTrace();}

            return Arrays.toString(sa);

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public String[] processData(String str){
        str=str.substring(19);
        String[] sa=str.split(",");
        sa[1]=" "+sa[1].substring(0,2)+":"+sa[1].substring(2,4)+":"+sa[1].substring(4,6);
        sa=new String[]{sa[0],sa[17]+sa[1].substring(0,6),sa[2].replace(".00",""),sa[3].replace(".00",""),sa[4].replace(".00",""),sa[8].replace(".00",""),sa[14],sa[14]};
//        return str.substring(0,8)+str.substring(117)+" "+str.substring(8,10)+":"+str.substring(10,12)+":"+str.substring(12,14)+str.substring(14,38).replace(".00","")+str.substring(62,71).replace(".00","")+str.substring(95,110);

        // 直接操作字符串会有问题 ，因为其中有几个数据 可能是3位 也可能是4位
        return sa;
    }
}
