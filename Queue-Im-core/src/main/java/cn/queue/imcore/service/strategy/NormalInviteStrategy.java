package cn.queue.imcore.service.strategy;

import cn.queue.domain.dto.GroupInviteDTO;
import cn.queue.domain.entity.AuditListEntity;
import cn.queue.domain.entity.GroupMemberEntity;
import cn.queue.domain.valueObj.GroupConstant;
import cn.queue.domain.valueObj.GroupStatusEnum;
import cn.queue.domain.valueObj.GroupTypeEnum;
import cn.queue.imcore.dao.IAuditListDAO;
import cn.queue.imcore.dao.IGroupUserDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NormalInviteStrategy implements InviteStrategy {

    @Resource
    private IGroupUserDao groupUserDao;
    @Resource
    private IAuditListDAO auditListDAO;

    @Override
    public boolean isSupport(GroupInviteDTO groupInviteDTO) {
        QueryWrapper<GroupMemberEntity> queryWrapper = new QueryWrapper<GroupMemberEntity>()
                .eq("group_id", groupInviteDTO.getGroupId())
                .eq("member_id", groupInviteDTO.getInviterId());
        GroupMemberEntity groupMemberEntity = groupUserDao.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(groupMemberEntity)) {
            return false;
        }
        if (groupMemberEntity.getRole() == GroupConstant.NORMAL_TYPE_CODE) {
            return true;
        }

        return false;
    }

    @Override
    public void invite(GroupInviteDTO groupInviteDTO) {
        groupInviteDTO.getUserIds().forEach(userId -> {
            AuditListEntity auditList = AuditListEntity.builder()
                    .createTime(new Date())
                    .userId(userId)
                    .inviterId(groupInviteDTO.getInviterId())
                    .status(GroupStatusEnum.NO_AUDIT.getCode())
                    .type(GroupTypeEnum.INVITED.getCode())
                    .build();
            auditListDAO.insert(auditList);
        });
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getName() {
        return "普通成员邀请规则";
    }

    @PostConstruct
    @Override
    public void register() {
        InviteStrategyFactory.register(this);
    }
}
