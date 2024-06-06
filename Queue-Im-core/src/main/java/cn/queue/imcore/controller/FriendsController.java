package cn.queue.imcore.controller;

import cn.queue.common.annnotation.LarryController;

import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.vo.AddRecordVO;
import cn.queue.domain.vo.FriendVO;
import cn.queue.imcore.service.impl.FriendsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:17
 * @Description:
 */
@LarryController
@RequestMapping("/friend")
public class FriendsController {
    @Resource
    private FriendsServiceImpl friendsService;

    @GetMapping("/queryPageFriend")
    public List<FriendVO> queryPageFriend(@RequestParam Long id, @RequestParam Integer pageSize,@RequestParam Integer pageNum){

        return friendsService.queryPageFriend(id, pageSize, pageNum);
    }

    @PostMapping("/addFriends")
    public String addFriends(@RequestParam Long fromId, @RequestParam Long toId,
                             @RequestParam String  note, @RequestParam String remark){
        AddRecordDTO addRecordDTO = new AddRecordDTO()
                .builder()
                .fromId(fromId)
                .toId(toId)
                .note(note)
                .remark(remark)
                .build();
        return friendsService.addFriend(addRecordDTO);
    }

    @PostMapping("/receiveAdd")
    public String receiveAdd(@RequestParam Long fromId, @RequestParam Long toId,
                             @RequestParam Integer status){
        return friendsService.receiveAdd(fromId, toId, status);
    }

    @GetMapping("/getApplyList")
    public List<AddRecordVO> getApplyList(@RequestParam Long id){
        return friendsService.getApplyList(id);
    }

    @PostMapping("/setRemark")
    public String setRemark(@RequestParam Long fromId, @RequestParam Long toId,
                            @RequestParam String remark){
        return friendsService.setRemark(fromId, toId, remark);
    }

    @PostMapping("/deleteFriend")
    public String deleteFriend(@RequestParam Long fromId, @RequestParam Long toId){
        return friendsService.deleteFriend(fromId, toId);
    }

    @PostMapping("/blackFriend")
    public String blackFriend(@RequestParam Long fromId, @RequestParam Long toId){
        return friendsService.blackFriend(fromId, toId);
    }

    @PostMapping("/setFriendsClazz")
    public String setFriendsClazz(@RequestParam Long fromId, @RequestParam Long toId,
                                  @RequestParam Long clazzId){
        return friendsService.setFriendsClazz(fromId, toId, clazzId);
    }


    @PostMapping("/addClazz")
    public String addClazz(@RequestParam Long id, @RequestParam String clazzName){
        return friendsService.addClazz(id, clazzName);
    }

    @PostMapping("/deleteClazz")
    public String deleteClazz(@RequestParam Long id, @RequestParam Long clazzId){
        return friendsService.deleteClazz(id, clazzId);
    }

}
