package cn.queue.common.domain.entity;

import lombok.*;

/**
 * @author: Larry
 * @Date: 2024 /05 /18 / 20:00
 * @Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserEntity {
    private Long userId;
    private String username;
    private String password;
    private String avtar;
    private Integer status;
}
