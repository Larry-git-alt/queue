package cn.queue.forum.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.forum.domain.dto.DynamicDTO;
import cn.queue.forum.domain.entity.Test;
import cn.queue.forum.mapper.DynamicMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 马兰友
 * @Date: 2024/05/18/15:18
 */
@LarryController

public class TestController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/test")
    public DynamicDTO test01 () {
        stringRedisTemplate.opsForSet().add("test", "test");
     return new DynamicDTO();
    }





}
