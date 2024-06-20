package org.example.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

@Data
public class StrategyRule {

    //自增id
    private Long id;

    //策略id
    private Long strategyId;

    //奖品id
    private Integer awardId;

    //规则类型
    private Integer ruleType;

    //规则模型
    private String ruleModel;

    //规则值
    private String ruleValue;

    //规则描述
    private String ruleDesc;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;


}
