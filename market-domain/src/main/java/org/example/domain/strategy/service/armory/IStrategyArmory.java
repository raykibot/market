package org.example.domain.strategy.service.armory;

import org.springframework.web.bind.annotation.Mapping;

public interface IStrategyArmory {


    boolean assemLottery(Long strategyId);

    Integer getRandomAwardId(Long strategyId);

}
