package cn.queue.common.config;
import cn.hutool.json.JSONUtil;
import cn.queue.common.annnotation.LarryController;
import cn.queue.common.domain.CommonResult;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;



@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 标注了@Code33Controller，且类及方法上都没有标注@IgnoreCosmoResult的方法才进行包装
        return methodParameter.getDeclaringClass().isAnnotationPresent(LarryController.class);
    }

    @Override
    public Object beforeBodyWrite(Object data,
                                  @NonNull MethodParameter methodParameter,
                                  @NonNull MediaType mediaType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> aClass,
                                  @NonNull ServerHttpRequest serverHttpRequest,
                                  @NonNull ServerHttpResponse serverHttpResponse) {
        // 防止过渡封装
        if (data instanceof CommonResult) {
            return data;
        }

        CommonResult<Object> success = CommonResult.success(data);
        return data instanceof String ? JSONUtil.toJsonStr(success) :  success ;
    }
}
