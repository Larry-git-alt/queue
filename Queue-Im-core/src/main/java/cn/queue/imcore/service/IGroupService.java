package cn.queue.imcore.service;

import cn.queue.common.domain.entity.UserEntity;
import cn.queue.domain.entity.GroupEntity;


import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /20 / 10:05
 * @Description:
 */
public interface IGroupService {
    List<GroupEntity> getGroupList(Long userId);
    List<UserEntity> getUserByGroupId(Long groupId);

    /**
     * 创建群聊
     * @param group
     */
    void createGroup(GroupEntity group);
}
