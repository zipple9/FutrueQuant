package com.commonbase.util;


import java.util.UUID;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/9/8-23:11
 */
public class CommonUtils {


    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
