package cn.queue.imcore.domain.entity;

/**
 * @author: Larry
 * @Date: 2024 /05 /19 / 13:47
 * @Description:
 */
public class GroupMemberEntity {
    private Long id;
    private Long groupId;
    private Long memberId;
    private String role;
    //是否处于禁用
    private Integer status;
}
