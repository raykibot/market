package org.example.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.infrastructure.persistent.po.Award;
import org.example.infrastructure.persistent.po.StrategyAward;

import java.util.List;

@Mapper
public interface IStrategyAwardDao {

    List<Award> queryAwardList();

    List<StrategyAward> queryStrategyAwardList(Long strategyId);
}
