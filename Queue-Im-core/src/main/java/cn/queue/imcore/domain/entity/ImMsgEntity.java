package cn.queue.imcore.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: Larry
 * @Date: 2024 /05 /02 / 10:11
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImMsgEntity {
    private Long id;

    private Long userId;

    private Long targetId;

    private LocalDateTime createTime;

    private String content;

    private Integer code;

    private String fileType;

}
