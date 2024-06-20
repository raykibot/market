package org.example.test.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.service.armory.IStrategyArmory;
import org.example.domain.strategy.service.armory.IStrategyDispatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IStrategyDispatch strategyDispatch;


    @Test
    public void test(){

        strategyArmory.assembleLottery(100001L) ;

    }

    @Test
    public void test1(){

        String name = "你好";

        System.out.println(name.concat("_世界"));

    }

    @Test
    public void getRandomAwardId(){
        log.info("测试结果：{} --奖品id",strategyDispatch.getRandomAwardId(100001L));

    }

    @Test
    public void getRandomAwardIdByRuleWeight(){

        log.info("测试结果：{} --奖品id", strategyDispatch.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} --奖品id", strategyDispatch.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} --奖品id", strategyDispatch.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));

    }



}
