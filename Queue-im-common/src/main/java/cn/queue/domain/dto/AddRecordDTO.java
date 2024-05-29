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

    private String username;
    //加好友时发的话

    private String note;
     //0未处理 1已同意 2 已拒绝
    private Integer status;
    //头像
    private String photo;
    //判断是申请的还是被申请的 1 是申请 0 是被申请
    private Integer type;

    private Date createTime;

    private Date updateTime;

    // type =1 status =0 ->等待验证
    // type =0 status =0 ->按钮-》选择 同意或者拒绝（1--2）
    // type =1,2 status =1,2 -> 直接渲染

}
