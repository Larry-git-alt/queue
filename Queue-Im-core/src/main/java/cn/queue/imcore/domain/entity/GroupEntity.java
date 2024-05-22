package cn.queue.imcore.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.ldap.PagedResultsControl;
import java.util.Date;

/**
 * @author: Larry
 * @Date: 2024 /05 /18 / 19:54
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupEntity {
   private Long id;
   //群名
   private String groupName;
   //群主
   private Long ownerId;
   //群简介
   private String intro;
   //群成员数量
   private Integer num;
   //群头像
   private String photo;
   //群状态 0正常 1解散
   private Integer status;
   //群成员上限
   private Integer maxMemberCount;
   //创建时间
   private Date createTime;

}
