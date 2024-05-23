package cn.queue.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @author: Larry
 * @Date: 2024 /05 /02 / 10:11
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@TableName("message")
public class ImMsgEntity{
    private Long id;

    private Long userId;

    private Long targetId;

    private String createTime;

    private String content;

    private Integer code;

    private String fileType;
//     "isRead":0
    //0表示未读 1表示已读
//    private Integer isRead;
//    //测试数据
//    {
//           "id":1,
//            "userId": 33
//            "targetId": 22,
//            "createTime": "2024-05-19T12:00:00",
//            "content": "Hello, this is a test message.",
//            "code": 1003,
//            "fileType": "text",
//    }

}
