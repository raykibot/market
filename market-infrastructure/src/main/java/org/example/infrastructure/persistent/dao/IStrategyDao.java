package org.example.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.domain.strategy.model.entity.StrategyEntity;
import org.example.infrastructure.persistent.po.Strategy;

@Mapper
public interface IStrategyDao {

    Strategy getStrategy(Long strategyId);


}
