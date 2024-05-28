package cn.queue.imcore.cache;

import cn.queue.domain.dto.AddRecordDTO;
import cn.queue.domain.valueObj.CacheConstant;
import cn.queue.domain.vo.AddRecordVO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddListCache {

    @Resource
    private RedisTemplate<String, AddRecordVO> redisTemplate;

    public void addToApplyList(AddRecordVO addRecordVO, Long id) {
        String key = CacheConstant.ADD_FRIENDS_APPLY + id;
        redisTemplate.opsForList().leftPush(key, addRecordVO);
    }

    /**
     * 清理缓存
     * @param id
     */
    public void cleanCache(Long id){
        String key = CacheConstant.ADD_FRIENDS_APPLY + id;
        redisTemplate.delete(key);
    }

    public List<AddRecordVO> queryAddListCache(Long id){
        String key = CacheConstant.ADD_FRIENDS_APPLY + id;
        RedisOperations<String, AddRecordVO> operations = redisTemplate.opsForList().getOperations();

        return redisTemplate.opsForList()
                .range(key, 0, -1);
    }
}
