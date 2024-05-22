package cn.queue.forum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author doar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DynamicDTO {
    private Long id;
    private String content;  //内容
    private String url;  //文件链接
    private Long pid;  //父动态id
    private Long targetId;  //回复人id
}
