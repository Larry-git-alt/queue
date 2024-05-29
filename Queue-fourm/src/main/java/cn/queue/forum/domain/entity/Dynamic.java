package cn.queue.forum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author doar
 * @date  2020/12/07
 *   动态表
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dynamic {
    private Long id;
    private Long userId;   //发布人id
    private String content;  //内容
    private String url;  //文件链接
    private Long pid;  //父动态id
    private Long targetId;  //回复人id
    private Long likes;  //点赞数
    private LocalDateTime createTime;  //创建时间
    private LocalDateTime updateTime;  //更新时间
    private Long createBy;  //创建人
    private Long updateBy;  //更新人
}
