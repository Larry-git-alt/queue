package cn.queue.imcore.service.impl;

import cn.queue.common.domain.entity.UserEntity;
import cn.queue.imcore.dao.IGroupDao;
import cn.queue.imcore.dao.IGroupUserDao;
import cn.queue.imcore.domain.entity.GroupEntity;
import cn.queue.imcore.domain.entity.GroupMemberEntity;
import cn.queue.imcore.service.IGroupService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
}
