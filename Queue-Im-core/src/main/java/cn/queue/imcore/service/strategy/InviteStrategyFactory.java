package cn.queue.imcore.service.strategy;

import cn.queue.common.exception.define.BizException;
import cn.queue.domain.dto.GroupInviteDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class InviteStrategyFactory {

    private static List<InviteStrategy> INVITE_STRATEGY_LIST = new ArrayList<>();

    public InviteStrategy getInviteStrategy(GroupInviteDTO groupInviteDTO) {
        InviteStrategy inviteStrategy = null;

        for (InviteStrategy strategy : INVITE_STRATEGY_LIST) {
            if (strategy.isSupport(groupInviteDTO)) {
                if (ObjectUtils.isEmpty(inviteStrategy)) {
                    inviteStrategy = strategy;
                } else {
                    if (strategy.getPriority() == inviteStrategy.getPriority()) {
                        throw new BizException(444, "规则优先级冲突!");
                    }
                    else if (strategy.getPriority() > inviteStrategy.getPriority()) {
                        inviteStrategy = strategy;
                    }
                }
            }
        }
        if (ObjectUtils.isEmpty(inviteStrategy)) {
            throw new BizException(444, "没有匹配的群组邀请规则!");
        } else {
            return inviteStrategy;
        }
    }

    public static void register(InviteStrategy inviteStrategy) {
        INVITE_STRATEGY_LIST.add(inviteStrategy);
    }
}
