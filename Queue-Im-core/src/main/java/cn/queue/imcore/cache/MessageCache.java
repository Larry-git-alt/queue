package cn.queue.imcore.cache;

import cn.hutool.json.JSONUtil;
import cn.queue.common.exception.define.BizException;
import cn.queue.common.util.RedisUtil;
import cn.queue.common.util.SnowUtil;
import cn.queue.domain.dto.GetMessageDTO;
import cn.queue.domain.dto.MemberDTO;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.valueObj.CacheConstant;
import cn.queue.domain.valueObj.ImMsgCodeEnum;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author: Larry
 * @Date: 2024 /05 /22 / 21:42
 * @Description:
 */
@Component
public class MessageCache {
    @Resource
    private RedisTemplate<String,ImMsgEntity> redisTemplate;
    public void addCache(ImMsgEntity imMsg){
        String key ="";
        if(imMsg.getCode() ==ImMsgCodeEnum.IM_GROUP_MSG.getCode()){
            key = CacheConstant.MESSAGE_GROUP_CACHE +"||"+imMsg.getTargetId();
            redisTemplate.opsForList().leftPush(key, imMsg);
        } else if (imMsg.getCode() == ImMsgCodeEnum.IM_USER_MSG.getCode()) {
            //两人对话的一个唯一标识
            String mark = Math.max(imMsg.getUserId(), imMsg.getTargetId())+ "and" + Math.min(imMsg.getUserId(), imMsg.getTargetId());
            key =CacheConstant.MESSAGE_USR_CACHE + "||"+mark;
            redisTemplate.opsForList().leftPush(key, imMsg);
        }
    }
    //获取历史会话
    public List<ImMsgEntity> getListByTargetId(GetMessageDTO getMessageDTO){
        String baseKey = determineBaseKey(getMessageDTO);
        checkMsgQueue(baseKey);
        // 确保这里有获取RedisTemplate的方法
        //如果redis过期返回null
        if(baseKey ==null){
            return null;
        }
        long total = 100L;
        long start = (getMessageDTO.getPageNum() - 1) * getMessageDTO.getPageSize();
        long end = Math.min(start + getMessageDTO.getPageSize() - 1, total - 1); // 使用 total - 1
        return redisTemplate.opsForList().range(baseKey,start,end);
    }
    //获得会话中最后一条消息
    public ImMsgEntity getLastMessage(GetMessageDTO getMessageDTO) {
        String baseKey = determineBaseKey(getMessageDTO); // 假设这是确定baseKey的方法
        if (baseKey == null) {
            // 处理错误情况，例如返回null或抛出异常
            return null;
        }

        // 确保这里有获取RedisTemplate的方法
        Long size = redisTemplate.opsForList().size(baseKey);
        if (size == null || size == 0) {
            // 列表为空或不存在
            return null;
        }
        long lastIndex = size - 1;
        return redisTemplate.opsForList().index(baseKey, lastIndex);

    }

    // 用于确定baseKey的方法
    private String determineBaseKey(GetMessageDTO getMessageDTO) {
        if (getMessageDTO.getCode() == ImMsgCodeEnum.IM_GROUP_MSG.getCode()) {
            return CacheConstant.MESSAGE_GROUP_CACHE + "||" + getMessageDTO.getTargetId();
        } else if (getMessageDTO.getCode() == ImMsgCodeEnum.IM_USER_MSG.getCode()) {
            String mark = Math.max(getMessageDTO.getUserId(), getMessageDTO.getTargetId())
                    + "and"
                    + Math.min(getMessageDTO.getUserId(), getMessageDTO.getTargetId());
            return CacheConstant.MESSAGE_USR_CACHE + "||" + mark;
        }
        return null; // 如果不匹配任何已知代码，返回null
    }
    //如果消息队列满了，就移除最后一条消息,确保redis空间
    private void checkMsgQueue(String key){
        Long size= redisTemplate.opsForList().size(key);
        if(size!=null && size >= 100){
            redisTemplate.opsForList().rightPop(key);
        }
    }
}
