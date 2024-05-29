package cn.queue.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupRuleEntity {

    /**
     * id 主键自增
     */
    private Long id;

    /**
     * 群聊id
     */
    private Long groupId;

    /**
     * 是否允许群成员邀请好友加群 0不允许 1允许
     */
    private Integer allowInvite;

    /**
     * 邀请类型 0无需审核直接进群 1需管理员审核
     */
    private Integer inviteType;
}
