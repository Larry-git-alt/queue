package cn.queue.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupInviteDTO {

    /**
     * 群聊id
     */
    private Long groupId;

    /**
     * 被邀请人列表
     */
    private List<Long> userIds;

    /**
     * 邀请人
     */
    private Long inviter_id;
}
