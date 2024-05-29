package cn.queue.domain.dto;


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
public class AddRecordDTO {

    private Long id;

    private Long fromId;

    private Long toId;

    private Integer status;

    private String note;

    private String remark;

    private Date createTime;

}
