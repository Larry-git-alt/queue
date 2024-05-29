package cn.queue.domain.dto;


import lombok.Data;

@Data
public class PageDTO {
    //页码
    public Integer page;
    //每页大小
    public Integer size;

}
