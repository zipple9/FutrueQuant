package com.commonbase.util;


import java.util.List;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2022/1/4-21:38
 */
public class WCollectionUtils {


    public  static <T> String listToString(List<T> list){
        StringBuilder str = new StringBuilder();
        list.forEach(i->{
            str.append(i.toString());
        });
        return str.toString();
    }
}
