package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Larry
 * @Date: 2024 /05 /18 / 20:47
 * @Description:
 */
@LarryController
public class TestController {
    @GetMapping("/test")
    public String test(){

        return "hello";
    }

}
