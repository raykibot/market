package org.example.domain.strategy.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.model.entity.StrategyAwardEntity;
import org.example.domain.strategy.model.entity.StrategyEntity;
import org.example.domain.strategy.model.entity.StrategyRuleEntity;
import org.example.domain.strategy.repository.IStrategyRepository;
import org.example.types.common.Constants;
import org.example.types.enums.ResponseCode;
import org.example.types.exception.AppException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 *
 * 装配工厂实现类
 *
 */
@Service
@Slf4j
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch{

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public boolean assembleLottery(Long strategyId) {

        //1.查询策略
        List<StrategyAwardEntity> list = strategyRepository.queryStrategyAwardList(strategyId);
        assembleLottery(String.valueOf(strategyId),list);

        //2.权重策略配置
        StrategyEntity strategyEntity = strategyRepository.getStrategyEntity(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();
        if (ruleWeight == null) return true;

        StrategyRuleEntity strategyRuleEntity = strategyRepository.queryStrategyRule(strategyId,ruleWeight);
        if (strategyRuleEntity == null) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(),ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValuesMap = strategyRuleEntity.getRuleWeightValues();

        Set<String> keys = ruleWeightValuesMap.keySet();

        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValuesMap.get(key);
            ArrayList<StrategyAwardEntity> listClone = new ArrayList<>(list);
            listClone.removeIf(entity->!ruleWeightValues.contains(entity.getAwardId()));
            assembleLottery(String.valueOf(strategyId).concat("_").concat(key),listClone);
        }

        return true;
    }

    private void assembleLottery(String key,List<StrategyAwardEntity> list){

        // 2. 获取最小概率值
        BigDecimal minAwardRate = list.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 3. 获取概率值总和
        BigDecimal totalAwardRate = list.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. 用 1 % 0.0001 获得概率范围，百分位、千分位、万分位
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 5. 生成策略奖品概率查找表「这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高」
        List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAward : list) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();
            // 计算出每个概率值需要存放到查找表的数量，循环填充
            for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }

        // 6. 对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchRateTables);

        // 7. 生成出Map集合，key值，对应的就是后续的概率值。通过概率来获得对应的奖品ID
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTable = new LinkedHashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTables.get(i));
        }

        // 8. 存放到 Redis
        strategyRepository.storeStrategyAwardSearchRateTable(key, rateRange, shuffleStrategyAwardSearchRateTable);

    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {

        //1.获取概率范围
        int rateRange = strategyRepository.getrateRange(strategyId);

        return strategyRepository.getStrategyAward(String.valueOf(strategyId),new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {

        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        System.out.println(key);

        int rateRange = strategyRepository.getrateRange(key);

        return strategyRepository.getStrategyAward(key,new SecureRandom().nextInt(rateRange));
    }
}
