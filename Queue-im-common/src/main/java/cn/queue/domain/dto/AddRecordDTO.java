package cn.queue.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRecordDTO {

    private Long id;

    private Long fromId;

    private Long toId;

    private String note;

    private Integer status;

    private String photo;
    //判断是申请的还是被申请的 1 是申请 0 是被申请
    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
