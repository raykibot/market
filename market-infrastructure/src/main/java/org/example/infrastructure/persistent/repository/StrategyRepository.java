package org.example.infrastructure.persistent.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.model.entity.StrategyAwardEntity;
import org.example.domain.strategy.model.entity.StrategyEntity;
import org.example.domain.strategy.model.entity.StrategyRuleEntity;
import org.example.domain.strategy.repository.IStrategyRepository;
import org.example.infrastructure.persistent.dao.IStrategyAwardDao;
import org.example.infrastructure.persistent.dao.IStrategyDao;

import org.example.infrastructure.persistent.dao.IStrategyRuleDao;
import org.example.infrastructure.persistent.po.Strategy;
import org.example.infrastructure.persistent.po.StrategyAward;
import org.example.infrastructure.persistent.po.StrategyRule;
import org.example.infrastructure.redis.IRedisService;
import org.example.types.common.Constants;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础设施层仓储实现类
 * -----负责数据具体实现和持久化
 **/
@Repository
@Slf4j
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyRuleDao strategyRuleDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IRedisService redisService;


    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {

        //从redis中获取数据
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> list = redisService.getValue(cacheKey);
        if (list != null && !list.isEmpty()) return list;

        //从数据库中获取数据
        List<StrategyAward> awardList = strategyAwardDao.queryStrategyAwardList(strategyId);
        list = new ArrayList<>(awardList.size());
        for (StrategyAward strategyAward : awardList) {
            StrategyAwardEntity strategyAwardEntity = new StrategyAwardEntity();
            strategyAwardEntity.setStrategyId(strategyAward.getStrategyId());
            strategyAwardEntity.setAwardId(strategyAward.getAwardId());
            strategyAwardEntity.setAwardCount(strategyAward.getAwardCount());
            strategyAwardEntity.setAwardCountSurplus(strategyAward.getAwardCountSurplus());
            strategyAwardEntity.setAwardRate(strategyAward.getAwardRate());


            list.add(strategyAwardEntity);
        }

        //数据库中获取到的数据存到redis中
        redisService.setValue(cacheKey, list);

        return list;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, BigDecimal rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTable) {


        //1.存储抽奖策略范围
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange.intValue());

        //2.存储抽奖策略范围
        Map<Integer, Integer> map = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);

        map.putAll(shuffleStrategyAwardSearchRateTable);
    }

    @Override
    public int getrateRange(Long strategyId) {
        return getrateRange(String.valueOf(strategyId));
    }

    @Override
    public int getrateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAward(String key, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public StrategyEntity getStrategyEntity(Long strategyId) {
        //优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (strategyEntity != null) return strategyEntity;
        Strategy strategy = strategyDao.getStrategy(strategyId);

        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();

        redisService.setValue(cacheKey, strategyEntity);

        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {

        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setRuleModel(ruleModel);
        StrategyRule strategyRuleByDatabase = strategyRuleDao.queryStrategyRule(strategyRule);

        return StrategyRuleEntity.builder()
                .awardId(strategyRuleByDatabase.getAwardId())
                .strategyId(strategyRuleByDatabase.getStrategyId())
                .ruleType(strategyRuleByDatabase.getRuleType())
                .ruleValue(strategyRuleByDatabase.getRuleValue())
                .ruleDesc(strategyRuleByDatabase.getRuleDesc())
                .ruleModel(strategyRuleByDatabase.getRuleModel())
                .build();

    }

}
