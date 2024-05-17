package cn.queue.common.exception.define;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Larry
 * @time: 2023/12/19 15:22
 * @description: 自定义业务异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizException extends RuntimeException {

    private Integer code;

    private String message;
}
