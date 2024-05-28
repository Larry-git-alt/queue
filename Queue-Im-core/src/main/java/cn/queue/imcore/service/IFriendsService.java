package cn.queue.imcore.service;


import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.vo.AddRecordVO;
import cn.queue.domain.vo.FriendVO;

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

    List<AddRecordVO> getApplyList(Long id);

    String addClazz(Long id, String clazzName);

    String deleteClazz(Long id, Long clazzId);

    String setFriendsClazz(Long fromId, Long toId, Long clazzId);

    List<FriendVO> queryFriendByClazz(Long id, Integer clazzId);

    String deleteFriend(Long fromId, Long toId);

    String blackFriend(Long fromId, Long toId);
}
