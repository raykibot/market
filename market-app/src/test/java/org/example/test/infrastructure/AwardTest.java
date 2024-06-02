package org.example.test.infrastructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.infrastructure.persistent.dao.IStrategyAwardDao;
import org.example.infrastructure.persistent.po.Award;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardTest {


    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Test
    public void test() {

        List<Award> awards = strategyAwardDao.queryAwardList();
        log.info("结果：{}", JSON.toJSONString(awards));

    }


}
