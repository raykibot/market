package org.example.test;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.service.armory.IStrategyArmory;
import org.example.infrastructure.persistent.dao.IStrategyAwardDao;
import org.example.infrastructure.persistent.po.Award;
import org.example.infrastructure.redis.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IRedisService redisService;

    @Resource
    private IStrategyArmory strategyArmory;

    @Test
    public void test() {
        log.info("测试完成");
    }

    @Test
    public void test1(){

        strategyArmory.assemLottery(1001L);

    }


    @Test
    public void test2(){
       log.info("测试结果：{}",strategyArmory.getRandomAwardId(1001L).toString());
       log.info("测试结果：{}",strategyArmory.getRandomAwardId(1001L).toString());
       log.info("测试结果：{}",strategyArmory.getRandomAwardId(1001L).toString());
    }

}
