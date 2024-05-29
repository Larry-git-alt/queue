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
   //消息类型
    private Integer code;
//文件类型
    private String fileType;
//    消息序列号
    private Long sequence;
//    //测试数据
//    {
//            "userId": 33,
//            "targetId": 22,
//            "content": "Hello, this is a test message.",
//            "code": 1003,
//            "fileType": "text",
//    }

}
