package cn.queue.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("add_list")
public class AddRecordEntity {

    private Long id;

    private Long fromId;

    private Long toId;

    private String note;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
