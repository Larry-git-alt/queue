package cn.queue.online_judge.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private Long id;//主键id
    private Long userId;//用户id
    private Long proId;//题目id
    private LocalDateTime submissionTime;//提交时间
    private String status;//提交状态
    private Integer runtime;//运行时间
    private String language;//语言
//    private Integer mode;//1普通 2挑战
    @TableField("`runspace`")
    private Integer runspace;//通过数据
    private Integer pass;//通过数据
    private Integer total;//总数据数
    private String code;//代码
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
    private Integer deleted;//是否删除 0未删除 1删除
 }
