package cn.queue.imcore.cache;

import cn.queue.domain.dto.AddRecordDTO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddListCache {

    @Resource
    private RedisTemplate<String, AddRecordDTO> redisTemplate;

    public void addToApplyList(AddRecordDTO addRecordDTO, Long id) {
        redisTemplate.opsForList().leftPush("queue:im:addFriendsApply:" + id + ":", addRecordDTO);

    }
}
