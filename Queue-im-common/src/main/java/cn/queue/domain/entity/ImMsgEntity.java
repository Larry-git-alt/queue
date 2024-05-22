package cn.queue.domain.entity;

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
public class ImMsgEntity {
    private Long id;

    private Long userId;

    private Long targetId;

    private LocalDateTime createTime;

    private String content;

    private Integer code;

    private String fileType;
    //测试数据
//    {
//        "id": 1,
//            "userId": 11,
//            "targetId": 22,
//            "createTime": "2024-05-19T12:00:00",
//            "content": "Hello, this is a test message.",
//            "code": 1003,
//            "fileType": "text"
//    }

}
