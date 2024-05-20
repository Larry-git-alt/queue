package cn.queue.imcore.domain.entity;

import javax.naming.ldap.PagedResultsControl;

/**
 * @author: Larry
 * @Date: 2024 /05 /18 / 19:54
 * @Description:
 */
public class GroupEntity {
   private Long id;
   //群主
   private String groupName;
   //群主
   private String ownerId;
   //群简介
   private String info;
   //群头像
   private String photo;
   //群状态 0正常 1解散
   private Integer status;
   //群成员上限
   private Integer maxMemberCount;

}
