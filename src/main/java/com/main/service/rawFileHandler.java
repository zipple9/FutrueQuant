package com.main.service;


import com.main.domain.FutureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class rawFileHandler {
    @Autowired
    myService myService;


    public List<FutureData> readFile(){
        List<String> dataList=new ArrayList<>();
        try{
            // Java8用流的方式读文件，更加高效
            Files.lines(Paths.get("D:\\tdx\\T0002\\export\\30#RBL8.txt"), StandardCharsets.UTF_8).forEach(x->{
////                System.out.println(x);

                dataList.add(x);
            });

        }catch (Exception e){e.printStackTrace();}
        List<FutureData> resultList=new ArrayList<>();

        for(int i=2;i<dataList.size()-1;i++){
            String temp="";
            temp=dataList.get(i).substring(0,dataList.get(i).length()-3);
            temp=temp.replaceFirst(","," ");

            String[] dataArray=temp.split(",");

//            System.out.println(ArrayUtil.toString(dataArray));

            FutureData futureData=new FutureData(dataArray[0],Integer.parseInt(dataArray[1]),Integer.parseInt(dataArray[2]),Integer.parseInt(dataArray[3]),Integer.parseInt(dataArray[4]),Integer.parseInt(dataArray[5]),Integer.parseInt(dataArray[6]));

            resultList.add(futureData);
        }

        return resultList;
    }

    public  void wiriteDB(){

        readFile().forEach(i->{
            myService.addHistroyData(i);

        });

    }

}
