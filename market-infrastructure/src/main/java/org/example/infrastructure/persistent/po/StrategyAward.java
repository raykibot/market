package org.example.infrastructure.persistent.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StrategyAward {

    private Long id ;

    private Long strategyId ;

    private String awardId ;

    private String awardTitle;

    private String awardSubtitle;

    private Integer awardCount;

    private Integer awardCountSurplus;

    private BigDecimal awardRate;

    private Integer sort;

    private String ruleModels;

    private Date createTime;

    private Date updateTime;

}
