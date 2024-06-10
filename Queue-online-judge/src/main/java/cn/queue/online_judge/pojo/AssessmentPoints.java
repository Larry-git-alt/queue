package cn.queue.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentPoints {
    private Long id;//id
    private Long userId;//用户id
    private Long proId;//题目id
    private Long comId;//比赛id
    private LocalDateTime submissionTime;//提交时间
    private String status;//状态
    private Integer runtime;//运行时间
    private String language;//语言
    private Integer runspace;//运行空间
    private Integer pass;//通过数据
    private Integer total;//总数据数
    private Integer deleted;//是否删除


}
