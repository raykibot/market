package org.example.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class Strategy {

    //自增id
    private Long id;

    //策略id
    private Long strategyId;

    //策略描述
    private String strategyDesc;

    //规则模型
    private String ruleModels;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
