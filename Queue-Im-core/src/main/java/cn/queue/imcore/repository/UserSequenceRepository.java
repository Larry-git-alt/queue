package cn.queue.imcore.repository;


import cn.queue.domain.valueObj.Constants;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @author BanTanger 半糖
 * @Date 2023/4/9 14:16
 */
@Service
public class UserSequenceRepository {

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 记录用户所有模块: 好友、群组、会话的数据偏序
     * Redis Hash 记录
     * uid 做 key, 具体 seq 做 value
     * @param
     * @param seq
     */
    public void writeUserSeq(String userId, String conversationId, Long seq) {
        String key = Constants.RedisConstants.SeqPrefix + userId;
        redisTemplate.opsForHash().put(key, conversationId, seq);
    }
    public long getUserSeq(String userId, String conversationId) {
        String key = Constants.RedisConstants.SeqPrefix + userId;
        System.out.println(key);
        System.out.println(conversationId);
        System.out.println(redisTemplate.opsForHash().get(key,conversationId));
        Integer test = (Integer) redisTemplate.opsForHash().get(key,conversationId);
        long longValue = test.longValue();
        return longValue;
    }

}
