package cn.queue.online_judge.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComRank {
    private Long id;//id
    private Long userId;//用户id
    private Long comId;//比赛id
    private Integer totalScore;//总得分
    private Integer totalTime;//总用时
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
}
