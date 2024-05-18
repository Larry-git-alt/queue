package cn.queue.forum.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.common.exception.define.BizException;
import cn.queue.forum.domain.Test;
import cn.queue.forum.mapper.TestMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 马兰友
 * @Date: 2024/05/18/15:18
 */
@LarryController
//@RequestMapping("/test")
public class TestController {

    @Resource
    private TestMapper testMapper;

    @GetMapping("/test")
    public String test01 () {
     return "hello word";
    }

    @GetMapping
    public List<Test> test () {
        return testMapper.selectList(new LambdaQueryWrapper<>());
    }
}
