package cn.queue.online_judge.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 题目实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
     private Integer id;//ID
     private String title;//标题
     private String content;//描述
     private Integer timeLimit;//时间限制
     private Integer memoryLimit;//内存限制
     private Integer stackLimit;//栈限制
     private Short difficulty;//难度 1简单 2中等 3困难
     private String tags;//标签
     private String inputFormat;//输入格式
     private String outputFormat;//输出格式
     private String range;//数据范围
     private String languages;//语言列表
     private List<Example> examples;
}
