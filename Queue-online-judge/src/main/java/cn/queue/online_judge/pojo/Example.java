package cn.queue.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用例实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Example {
     private Integer id;//ID
     private Integer problemId;//题目ID
     private String input;//用例输入
     private String output;//用例输出

}
