package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;
import org.springframework.web.bind.annotation.GetMapping;

@LarryController
public class ZController {

    @GetMapping("/zz")
    public String zz()
    {

        return "zz";
    }


}
