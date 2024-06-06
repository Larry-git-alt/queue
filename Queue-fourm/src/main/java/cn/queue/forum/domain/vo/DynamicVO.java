package cn.queue.forum.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author doar
 * @data 2020/12/08
 *  返回的动态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynamicVO {
    private Long id;
    private Long userId;   //发布人id
    private String content;  //内容
    private String url;  //文件链接
    private Long pid;  //父动态id
    private Long targetId;  //回复人id
    private Long likes;  //点赞数
    private Long type;  //类型
    private Long topicId; //题目id
    private LocalDateTime createTime;  //创建时间
    private LocalDateTime updateTime;  //更新时间
    private Long createBy;  //创建人
    private UserVO userVO; //发布人信息
    private List<DynamicVO> children;  //子动态（回复）
}
