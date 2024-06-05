package cn.queue.online_judge.dto;

import lombok.Data;

@Data
public class QusPackDTO {
    private Long comId;
    private String language;
    private Long qusId;
    private String code;
    private Integer timeLimit;
    private Integer memoryLimit;
}
