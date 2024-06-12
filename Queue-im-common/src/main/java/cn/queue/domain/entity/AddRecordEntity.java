package cn.queue.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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

    private String remark;

    private Date createTime;

    private Date updateTime;

}
