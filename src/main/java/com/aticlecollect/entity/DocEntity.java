package com.aticlecollect.entity;


import lombok.Data;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: wzy
 * @Description:
 * @CreateTime: 2021/9/7-22:28
 */
@Data
public class DocEntity {

    private String docId;

    private String title;

    private String content;

    private LocalDateTime collectTime;

    private LocalDateTime publishDate;


    private String url;


    public static void main(String[] args) {

        LocalDateTime time = LocalDateTime.now();
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(time));
    }
}


