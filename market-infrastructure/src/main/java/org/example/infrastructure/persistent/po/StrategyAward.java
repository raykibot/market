package org.example.infrastructure.persistent.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 抽奖策略奖品明细配置表 -概率-规则
 */

@Data
public class StrategyAward {

    //自增id
    private Long id ;

    //抽奖策略id
    private Long strategyId ;

    //抽奖奖品id
    private Integer awardId ;

    //抽奖奖品标题
    private String awardTitle;

    //抽奖奖品副标题
    private String awardSubtitle;

    //奖品库存总量
    private Integer awardCount;

    //奖品库存剩余数量
    private Integer awardCountSurplus;

    //奖品中奖概率
    private BigDecimal awardRate;

    //排序
    private Integer sort;

    //规则模型
    private String ruleModels;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
