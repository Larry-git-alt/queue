package cn.queue.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 17:26
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendVO {
  private Long friendId;
  private String remark;
  private String photo;
}
