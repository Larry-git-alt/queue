package cn.queue.online_judge.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class Problem extends BaseEntity{
     @TableId
     private Long id;//ID
     @TableField("title")
     private String title;//标题
     @TableField("content")
     private String content;//描述
     @TableField("time_limit")
     private Integer timeLimit;//时间限制
     @TableField("memory_limit")
     private Integer memoryLimit;//内存限制
     @TableField("stack_limit")
     private Integer stackLimit;//栈限制
     @TableField("difficulty")
     private Short difficulty;//难度 1简单 2中等 3困难
     @TableField("tags")
     private String tags;//标签
     @TableField("input_format")
     private String inputFormat;//输入格式
     @TableField("output_format")
     private String outputFormat;//输出格式
     @TableField("`range`")
     private String range;//数据范围
     @TableField("languages")
     private String languages;//语言列表
     @TableField(exist = false)
     private List<Example> examples;
  //   @TableLogic(value = "0", delval = "1")
}
