package cn.queue.domain.pack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageReadContent {

    /** 消息已读偏序 */
    private long messageSequence;

    /** 要么 fromId + toId */
    private String fromId;
    private String toId;

    /** 要么 groupId */
    private String groupId;

    /** 标识消息来源于单聊还是群聊 */
    private Integer conversationType;

}