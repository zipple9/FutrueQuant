package com.main.util;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by wzy on 2019/1/17 21:57
 **/
@Component
public class historyDataUtil {

    public void txtToSQL(){
        try{
            BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\future_data\\RBM_1MIN"));
            br.readLine();

        }catch(Exception e){

        }

    }



}
