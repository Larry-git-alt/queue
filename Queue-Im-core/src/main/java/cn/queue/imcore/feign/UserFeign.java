package cn.queue.imcore.feign;


import cn.queue.base.user.domain.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "base")
public interface UserFeign {

    @GetMapping("base/user/getById")
    User getById (@RequestParam("id") Long id);

}
