package cn.queue.forum.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.common.exception.define.BizException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 马兰友
 * @Date: 2024/05/18/15:18
 */
@LarryController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test () {
        throw new BizException();
    }
}
