package cn.queue.imcore.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Long id;
    private String name;
    private String photo;
    //私聊、群聊
    private String type;
    //是否在线（私聊）
    private Integer isOnline;
    private String LastMessageContent;
    private String LastTime;
}
