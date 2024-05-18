package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Larry
 * @Date: 2024 /05 /18 / 13:53
 * @Description:
 */

@LarryController
public class TestController {

    @GetMapping("/test")
    public String get(){
        return "asdasdasdasdad";
    }
}
