package cn.queue.forum.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author doar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Integer page;
    private Integer size;
    private Long type;
}
