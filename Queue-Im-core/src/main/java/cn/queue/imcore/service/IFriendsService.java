package cn.queue.imcore.service;


import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.entity.FriendsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:08
 * @Description:
 */
public interface IFriendsService  {
    List<FriendsEntity> getList(Long id);

    String addFriend(Long fromId, Long toId, String note);

    String receiveAdd(Long fromId, Long toId, Integer status);

    boolean isFriend(Long fromId, Long toId);

    List<AddRecordDTO> getApplyList(Long id);
}
