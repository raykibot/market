package org.example.test;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.service.armory.IStrategyArmory;
import org.example.domain.strategy.service.armory.IStrategyDispatch;
import org.example.infrastructure.persistent.dao.IStrategyAwardDao;
import org.example.infrastructure.persistent.po.Award;
import org.example.infrastructure.redis.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IStrategyDispatch StrategyArmoryDispatch;

    @Resource
    private IStrategyArmory strategyArmory;

    @Test
    public void test() {
        log.info("测试完成");
    }

    @Test
    public void test1(){

        strategyArmory.assembleLottery(1001L);

    }


    @Test
    public void test2(){
       log.info("测试结果：{}",StrategyArmoryDispatch.getRandomAwardId(1001L).toString());
       log.info("测试结果：{}",StrategyArmoryDispatch.getRandomAwardId(1001L).toString());
       log.info("测试结果：{}",StrategyArmoryDispatch.getRandomAwardId(1001L).toString());
    }


    @Test
    public void test3(){

        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("one_key",1);
        map.put("two_key",2);
        map.put("three_key",3);
        System.out.println("map原始数据"+map);

        Set<String> set = new HashSet<>();
        System.out.println("set原始数据------"+set);
        set = map.keySet();
        System.out.println("set数据-----"+set);


    }

}
