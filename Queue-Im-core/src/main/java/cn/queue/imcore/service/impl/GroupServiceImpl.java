package cn.queue.imcore.service.impl;

import cn.queue.common.domain.entity.UserEntity;
import cn.queue.imcore.constant.GroupConstant;
import cn.queue.imcore.dao.IGroupDao;
import cn.queue.imcore.dao.IGroupUserDao;
import cn.queue.imcore.domain.entity.GroupEntity;
import cn.queue.imcore.domain.entity.GroupMemberEntity;
import cn.queue.imcore.service.IGroupService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Larry
 * @Date: 2024 /05 /20 / 10:58
 * @Description:
 */
@Service
public class GroupServiceImpl implements IGroupService {
   @Resource
   private IGroupDao groupDao;
   @Resource
   private IGroupUserDao groupUserDao;

   public List<GroupEntity> getGroupList(Long userId){
       LambdaQueryWrapper<GroupMemberEntity> groupMemberEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
       groupMemberEntityLambdaQueryWrapper.eq(userId!=null,GroupMemberEntity::getMemberId,userId);
       List<GroupMemberEntity> groupMemberEntityList = groupUserDao.selectList(groupMemberEntityLambdaQueryWrapper);
       return groupMemberEntityList.stream().map(
            groupMemberEntity -> groupDao.selectById(groupMemberEntity.getGroupId())
       ).toList();
   }

    @Override
    public List<UserEntity> getUserByGroupId(Long groupId) {
        LambdaQueryWrapper<GroupMemberEntity> groupMemberEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        groupMemberEntityLambdaQueryWrapper.eq(groupId!=null,GroupMemberEntity::getGroupId,groupId);
        //todo
        return null;
    }

    /**
     * 创建群聊
     * 本方法负责创建一个新的群聊，并将创建者设置为群主。
     * @param group 包含群聊信息的GroupEntity对象，其中应包含群聊的名称、所有者ID等必要信息。
     */
    @Override
    @Transactional
    public void createGroup(GroupEntity group) {
        // 设置群聊的创建时间
        group.setCreateTime(new Date());
        // 将群聊信息插入到群聊表中
        groupDao.insert(group);

        // 构建群成员信息，将群主加入到群聊中
        GroupMemberEntity groupMemberEntity = GroupMemberEntity.builder()
                .groupId(group.getId())
                .memberId(group.getOwnerId())
                .status(GroupConstant.DEFAULT_MEMBER_STATUS)
                .role(GroupConstant.OWNER_TYPE_CODE)
                .build();
        // 将群主的群成员信息插入到群成员表中
        groupUserDao.insert(groupMemberEntity);
    }

}
