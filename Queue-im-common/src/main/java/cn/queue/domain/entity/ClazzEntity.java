package cn.queue.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("clazz")
public class ClazzEntity {
    private Long id;
    private Long userId;
    private Long clazzId;
    private String clazzName;
}
