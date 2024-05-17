package cn.queue.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Larry
 * @time: 2023/12/19 10:15
 * @description: 通用返回对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> implements Serializable {
    private Integer code;

    private String message;

    private T data;



    /**
     * @return 成功提示信息
     */
    public static <T> CommonResult<T> success() {
        return CommonResult.<T>builder()
                .code(200)
                .message("操作成功")
                .build();
    }

    /**
     * @param data 返回值
     * @return 成功提示信息 + 返回值
     */
    public static <T> CommonResult<T> success(T data) {
        return CommonResult.<T>builder()
                .code(200)
                .message("操作成功")
                .data(data)
                .build();
    }

    /**
     * @param message 失败提示信息
     * @return 失败提示信息 + 返回值
     */
    public static <T> CommonResult<T> fail(String message) {
        return CommonResult.<T>builder()
                .code(500)
                .message(message)
                .build();
    }


    /**
     * @param code 默认返回状态信息
     * @param message 失败提示信息
     * @return 失败提示信息 + 返回值
     */
    public static <T> CommonResult<T> fail(Integer code,String message) {
        return CommonResult.<T>builder()
                .code(code)
                .message(message)
                .build();
    }

}
