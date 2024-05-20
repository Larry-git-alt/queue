package cn.queue.imcore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 13:41
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
   private String username;
   private String photo;
   //普通成员，群主，管理员
   private Integer role;
   //在线，离线
   private Integer status;
}
