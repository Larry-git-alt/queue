package cn.queue.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relation {
    private Long id;//id
    private Long problemId;//题目id
    private Long typeId;//类型ID
    private Integer type;//类型
    private Long moduleId;//模块id
}
