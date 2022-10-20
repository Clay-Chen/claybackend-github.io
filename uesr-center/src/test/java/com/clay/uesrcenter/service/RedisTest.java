package com.clay.uesrcenter.service;

import com.clay.uesrcenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    //Redis的操作对象
    @Resource
    private RedisTemplate redisTemplate;
//@Resource
//private StringRedisTemplate stringRedisTemplate;
    @Test
    void test(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //增
        valueOperations.set("clayString","dog");
        valueOperations.set("clayInt",1);
        valueOperations.set("clayDouble",2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("clay");
        valueOperations.set("clayUser",user);
        //查
        Object clay = valueOperations.get("clayString");
        Assertions.assertTrue("dog".equals((String)clay ));
         clay = valueOperations.get("clayInt");
         Assertions.assertTrue(1==(Integer)clay);
         clay = valueOperations.get("clayDouble");
         Assertions.assertTrue(2.0 == (Double)clay);
        System.out.println(valueOperations.get("clayUser"));

    }
}
