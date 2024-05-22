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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
