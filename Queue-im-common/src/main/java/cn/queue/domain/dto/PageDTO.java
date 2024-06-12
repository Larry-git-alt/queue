package cn.queue.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageDTO {
    //页码
    public Long pageSize;
    //每页大小
    public Long pageNum;

}
