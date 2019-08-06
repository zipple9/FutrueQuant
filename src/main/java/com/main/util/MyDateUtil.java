package com.main.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wzy
 * @Date 2019/7/30
 */
public class MyDateUtil {


    public static List<LocalDate> getEveryDay(String start, String end) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate dateStart = LocalDate.parse(start, dtf);
        LocalDate dateEnd = LocalDate.parse(end, dtf);
        LocalDate d = dateStart;
        List<LocalDate> result=new ArrayList<>();
        while(d.isBefore(dateEnd)){
            result.add(d);
            d=d.plusDays(1);
        }
        return result;


    }


    public static void main(String[] args) {


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate dateStart = LocalDate.parse("20190101", dtf);
        System.out.println(dateStart);

        System.out.println(dateStart.plusDays(1));

        getEveryDay("20190101","20190301").forEach(i-> System.out.println(i));

    }

}
