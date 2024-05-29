package cn.queue.forum.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author doar
 * @data 2020/12/08
 *   发布动态的用户信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private Long userId;
    private String nickname;
    private String img;
}
