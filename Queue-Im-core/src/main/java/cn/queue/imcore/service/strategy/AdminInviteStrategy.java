package cn.queue.imcore.service.strategy;

import cn.queue.domain.dto.GroupInviteDTO;
import cn.queue.domain.entity.GroupMemberEntity;
import cn.queue.domain.valueObj.GroupConstant;
import cn.queue.imcore.dao.IGroupUserDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class AdminInviteStrategy implements InviteStrategy {

    @Resource
    private IGroupUserDao groupUserDao;

    @Override
    public boolean isSupport(GroupInviteDTO groupInviteDTO) {
        QueryWrapper<GroupMemberEntity> queryWrapper = new QueryWrapper<GroupMemberEntity>()
                .eq("group_id", groupInviteDTO.getGroupId())
                .eq("member_id", groupInviteDTO.getInviter_id());
        GroupMemberEntity groupMemberEntity = groupUserDao.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(groupMemberEntity)) {
            return false;
        }
        if (groupMemberEntity.getRole() == GroupConstant.OWNER_TYPE_CODE ||
                groupMemberEntity.getRole() == GroupConstant.ADMIN_TYPE_CODE) {
            return true;
        }

        return false;
    }

    @Override
    public void invite(GroupInviteDTO groupInviteDTO) {
        groupInviteDTO.getUserIds().forEach(id -> {
            GroupMemberEntity groupMemberEntity = GroupMemberEntity.builder()
                    .groupId(groupInviteDTO.getGroupId())
                    .memberId(id)
                    .status(GroupConstant.DEFAULT_MEMBER_STATUS)
                    .role(GroupConstant.NORMAL_TYPE_CODE)
                    .build();
            groupUserDao.insert(groupMemberEntity);
        });
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getName() {
        return "管理员/群主邀请规则";
    }

    @PostConstruct
    @Override
    public void register() {
        InviteStrategyFactory.register(this);
    }
}
