package cn.queue.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRecordVO {

    private Long id;

    private Long fromId;

    private Long toId;

    private String username;

    private String note;

    private Integer status;
    //头像
    private String photo;
    //判断是申请的还是被申请的 1 是申请 0 是被申请
    private Integer type;

    private Integer isOnline;

    private Date createTime;

    private Date updateTime;

}
