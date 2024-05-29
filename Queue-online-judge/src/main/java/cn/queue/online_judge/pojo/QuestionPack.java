package cn.queue.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPack {
    private String language;
    private Long qusId;
    private String code;
    private Integer timeLimit;
    private Integer memoryLimit;

}
