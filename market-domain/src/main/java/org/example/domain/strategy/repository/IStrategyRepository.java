package org.example.domain.strategy.repository;

import org.example.domain.strategy.model.entity.StrategyAwardEntity;
import org.example.domain.strategy.model.entity.StrategyEntity;
import org.example.domain.strategy.model.entity.StrategyRuleEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 领域层仓储接口
 * -----不包含实现细节 只定义行为
 */

public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(String key, BigDecimal rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTable);

    int getrateRange(Long strategyId);

    int getrateRange(String key);

    Integer getStrategyAward(String key, int rateKey);

    StrategyEntity getStrategyEntity(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);
}
