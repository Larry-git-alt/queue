package cn.queue.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 讨论实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discussion extends BaseEntity{
    private Long id;//ID
    private Long problemId;//题目ID
    private Long userId;//用户ID
    private String title;//讨论标题
    private String  content;//讨论内容
    private Integer views;//浏览数
    private Integer support;//支持数
}
