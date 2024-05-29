package cn.queue.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 19:32
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMessageDTO extends PageDTO {
    private Long userId;
    private Long targetId;
    private Integer code;

}
