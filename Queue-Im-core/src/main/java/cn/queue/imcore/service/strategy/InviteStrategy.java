package cn.queue.imcore.service.strategy;

import cn.queue.domain.dto.GroupInviteDTO;

public interface InviteStrategy {
    // 条件是否匹配
    public boolean isSupport(GroupInviteDTO groupInviteDTO);

    // 策略
    public void invite(GroupInviteDTO groupInviteDTO);

    // 规则冲突时的优先级
    public int getPriority();

    // 规则名称
    public String getName();

    public void register();
}
