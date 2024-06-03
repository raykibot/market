package org.example.domain.strategy.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.model.entity.StrategyAwardEntity;
import org.example.domain.strategy.repository.IStrategyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 装配工厂实现类
 *
 */
@Service
@Slf4j
public class StrategyArmory implements IStrategyArmory{

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public void assemLottery(Long strategyId) {

        //查询策略
        List<StrategyAwardEntity> list = strategyRepository.queryStrategyAwardList(strategyId);

    }
}
