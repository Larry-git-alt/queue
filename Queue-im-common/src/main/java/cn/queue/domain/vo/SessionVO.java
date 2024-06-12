package cn.queue.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Larry
 * @Date: 2024 /05 /20 / 10:11
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionVO {
    private Long id; //会话id
    private Long toId;
    private Integer isOnline;
    private String name;//群名或者用户名
    private String photo;
    private Integer type;//会话类型（群聊、私聊）
    private String lastMessageContent;//最后一条消息的内容
    private String lastTime;//最后一条消息的时间
    private Long noReadMessage;//未读消息数
}


