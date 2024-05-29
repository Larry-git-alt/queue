package cn.queue.domain.dto;


import lombok.Data;

@Data
public class PageDTO {
    //页码
    private Integer page;
    //每页大小
    private Integer size;

}
