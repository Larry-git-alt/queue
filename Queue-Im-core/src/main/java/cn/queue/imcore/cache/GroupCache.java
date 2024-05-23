package cn.queue.imcore.cache;

import cn.queue.common.util.RedisUtil;
import cn.queue.domain.entity.FriendsEntity;
import cn.queue.domain.entity.GroupEntity;
import cn.queue.domain.valueObj.CacheConstant;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /23 / 15:56
 * @Description:
 */
@Component
public class GroupCache {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private RedisTemplate<String,GroupEntity> redisTemplate;

    public List<GroupEntity> getGroup(Long groupId){
        String key = CacheConstant.GROUP_LIST_CACHE+groupId;
        //最多只能加500个好友
        return redisTemplate.opsForList().range(key,0,50);
    }
}
