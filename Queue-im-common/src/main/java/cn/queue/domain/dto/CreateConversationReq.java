package cn.queue.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Larry
 * @date 2024/5/15 23:27
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateConversationReq {

    private Integer appId;

    /** 会话类型 */
    @NotBlank(message = "会话类型不能为空")
    private Integer conversationType;

    @NotBlank(message = "fromId 不能为空")
    private String fromId;

    /** 目标对象 Id 或者群组 Id */
    @NotBlank(message = "toId 不能为空")
    private String toId;

}
