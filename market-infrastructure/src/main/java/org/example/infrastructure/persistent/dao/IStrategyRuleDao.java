package org.example.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.infrastructure.persistent.po.StrategyRule;

@Mapper
public interface IStrategyRuleDao {

    StrategyRule queryStrategyRule(StrategyRule strategyRule);
}
