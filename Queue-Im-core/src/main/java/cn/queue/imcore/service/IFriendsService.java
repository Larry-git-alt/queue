package cn.queue.imcore.service;


import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.vo.AddRecordVO;
import cn.queue.domain.vo.FriendVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 16:08
 * @Description:
 */
public interface IFriendsService  {
    List<FriendsEntity> getList(Long id);

    String addFriend(AddRecordDTO addRecordDTO);

    String receiveAdd(Long fromId, Long toId, Integer status);

    boolean isFriend(Long fromId, Long toId);

    List<AddRecordVO> getApplyList(Long id);

    String addClazz(Long id, String clazzName);

    String deleteClazz(Long id, Long clazzId);

    String setFriendsClazz(Long fromId, Long toId, Long clazzId);

    List<FriendVO> queryFriendByClazz(Long id, Integer clazzId);

    List<FriendVO> queryPageFriend(Long id, Integer page, Integer size);

    String setRemark(Long fromId, Long toId, String remark);

    String deleteFriend(Long fromId, Long toId);

    String blackFriend(Long fromId, Long toId);
}
