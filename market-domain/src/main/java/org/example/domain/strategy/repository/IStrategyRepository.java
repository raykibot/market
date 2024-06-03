package org.example.domain.strategy.repository;

import org.example.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * 领域层仓储接口
 * -----不包含实现细节 只定义行为
 */

public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

}
