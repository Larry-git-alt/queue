package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.imcore.domain.entity.FriendsEntity;
import cn.queue.imcore.service.impl.FriendsServiceImpl;
import jakarta.annotation.Generated;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:17
 * @Description:
 */
@LarryController
public class FriendsController {
    @Resource
    private FriendsServiceImpl friendsService;

    @GetMapping("/get")
    public List<FriendsEntity> test(){
        //return friendsService.test();
        return null;
    }

}
