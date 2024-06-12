package cn.queue.imcore.repository;

import cn.queue.common.util.RedisUtil;
import cn.queue.domain.valueObj.ImConstants;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class HeartRepository {
   @Resource
   private RedisUtil redisUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
   private static final String cacheKeyPrefix = "HEART:IM";

    //移除超过两次以上未发送心跳包的用户
    public void removeExpireRecord(Long userId) {
        String key = cacheKeyPrefix + ":" + userId % 1000;
        redisUtil.removeExpireRecord(key, ImConstants.DEFAULT_HEART_BEAT_GAP);

    }


    public void buildHeartRecord(Long userId) {
        //根据id取模实现分片（后期加入appId取分业务）
        String key = cacheKeyPrefix + userId % 1000;
        redisUtil.addZet(userId,key);
        //设置过期时间，5分钟内没有任何心跳变化，移除key
        redisUtil.expire(key,300);
    }

  public void addTime(Long userId){
        //延长用户心跳时间
      stringRedisTemplate.expire(cacheKeyPrefix + ":" + userId, ImConstants.DEFAULT_HEART_BEAT_GAP * 2, TimeUnit.SECONDS);
  }

}