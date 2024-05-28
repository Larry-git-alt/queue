package cn.queue.imcore.cache;

import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.vo.AddRecordVO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddListCache {

    @Resource
    private RedisTemplate<String, AddRecordVO> redisTemplate;

    public void addToApplyList(AddRecordVO addRecordVO, Long id) {
        redisTemplate.opsForList().leftPush("queue:im:addFriendsApply:" + id + ":", addRecordVO);

    }
}
