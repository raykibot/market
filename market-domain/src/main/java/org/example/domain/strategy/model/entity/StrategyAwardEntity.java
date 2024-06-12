package org.example.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 *
 *策略奖品实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardEntity {

    //策略id
    private Long strategyId ;

    //奖品id
    private Integer awardId ;

    //奖品数量
    private Integer awardCount;

    //剩余奖品数量
    private Integer awardCountSurplus;

    //中奖概率
    private BigDecimal awardRate;



}
