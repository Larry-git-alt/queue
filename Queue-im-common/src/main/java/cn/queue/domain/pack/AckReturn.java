package cn.queue.domain.pack;

import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Larry
 * @Date: 2024 /05 /30 / 15:48
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AckReturn {
    private Long userId;
    private Long targetId;
    private Long sequence;
}
