package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;
import cn.queue.domain.vo.AddRecordVO;
import cn.queue.domain.vo.FriendVO;
import cn.queue.imcore.service.IFriendsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@LarryController
public class ZController {


    @Resource
    private IFriendsService friendsService;


    @GetMapping("/zz")
    public List<FriendVO> zz()
    {
        return friendsService.queryPageFriend(3L,1,1);
    }


}
