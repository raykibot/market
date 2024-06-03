package org.example.infrastructure.persistent.repository;

import org.example.domain.strategy.model.entity.StrategyAwardEntity;
import org.example.domain.strategy.repository.IStrategyRepository;
import org.example.infrastructure.persistent.dao.IStrategyAwardDao;
import org.example.infrastructure.persistent.po.Award;
import org.example.infrastructure.persistent.po.StrategyAward;
import org.example.infrastructure.redis.IRedisService;
import org.example.types.common.Constants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *基础设施层仓储实现类
 * -----负责数据具体实现和持久化
 **/

public class StrategyRepository implements IStrategyRepository {

    @Resource
    IStrategyAwardDao strategyAwardDao;

    @Resource
    private IRedisService redisService;


    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {

        //从redis中获取数据
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_key+strategyId;
        List<StrategyAwardEntity> list = redisService.getValue(cacheKey);
        if (list != null && !list.isEmpty()) return list;

        //从数据库中获取数据
        List<StrategyAward> awardList = strategyAwardDao.queryStrategyAwardList(strategyId);
        list = new ArrayList<>(awardList.size());
        for (StrategyAward strategyAward : awardList) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .awardId(strategyAward.getAwardId())
                    .strategyId(strategyAward.getStrategyId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();

            list.add(strategyAwardEntity);
        }

        //数据库中获取到的数据存到redis中
        redisService.setValue(cacheKey,list);

        return list;
    }
}
