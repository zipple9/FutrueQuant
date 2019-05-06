package com.main.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Auther: wzy
 * @Date: 2019/05/06
 */
@Data
public class Period {

    private Integer duringDays;

    private String startDate;
    private String endDate;

    private Double valueChange;

}
