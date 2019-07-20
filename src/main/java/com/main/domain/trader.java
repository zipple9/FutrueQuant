package com.main.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class trader {

    private String treaderName;
    private BigDecimal cash;
    private Map<String,Integer> holdings;
    private List<tradeLog> tradeLogs;

}
