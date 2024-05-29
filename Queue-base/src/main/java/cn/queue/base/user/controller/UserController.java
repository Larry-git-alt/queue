package cn.queue.base.user.controller;

import cn.queue.base.user.domain.entity.User;
import cn.queue.base.user.service.UserService;
import cn.queue.common.annnotation.LarryController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 马兰友
 * @Date: 2024/05/20/17:41
 */
@LarryController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getById")
    public User getById (@RequestParam("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<User> list () {
        return userService.list();
    }


}
