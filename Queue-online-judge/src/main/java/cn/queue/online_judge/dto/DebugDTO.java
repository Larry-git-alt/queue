package cn.queue.online_judge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebugDTO {
    private String language;
    private Long qusId;
    private String input;
    private String code;
    private Integer timeLimit;
    private Integer memoryLimit;
}
