package cn.queue.online_judge.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity{
    private Long id;//课程id
    private String title;//课程标题
    private Integer numbers;//参与人数
    private LocalDate startTime;//开始时间
    private LocalDate endTime;//截止时间
    private String content;//课程内容
    @TableField("`condition`")
    private Integer condition;//解锁条件
    @TableField(exist = false)
    private List<Module> modules;

}
