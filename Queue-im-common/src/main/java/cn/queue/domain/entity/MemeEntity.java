package cn.queue.domain.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@TableName("meme")
public class MemeEntity {
    //表情包id
    private Long id;

    private String name;

    private Long userId;

    private String content;

    private DateTime createTime;

    private DateTime updateTime;

}
