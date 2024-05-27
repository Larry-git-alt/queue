package cn.queue.imcore.cache;

import cn.queue.common.util.RedisUtil;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.valueObj.CacheConstant;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 15:57
 * @Description:
 */
@Component
public class FriendsCache {
    @Resource
    private RedisTemplate<String,FriendsEntity> redisTemplate;

    public List<FriendsEntity> getFriends(Long userId){
        String key = CacheConstant.FRIENDS_LIST_CACHE+userId;
        //最多只能加500个好友
        return redisTemplate.opsForList().range(key,0,500);
    }
}
