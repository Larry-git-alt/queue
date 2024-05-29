package cn.queue.online_judge.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module extends BaseEntity{
    private Long id;//主键id
    private Long courseId;//课程id
    private String title;//模块名称
    private String introduction;//模块介绍
    private Integer proNums;//题目数量
    @TableField(exist = false)
    private List<Problem> problems;

}
