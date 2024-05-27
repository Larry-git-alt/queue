package cn.queue.imcore.cache;

import cn.hutool.json.JSONUtil;
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
    //加载窗口初始会话
    public List<ImMsgEntity> getListByTargetId(GetMessageDTO getMessageDTO){
        String baseKey;
        if(getMessageDTO.getCode() == ImMsgCodeEnum.IM_GROUP_MSG.getCode()){
            baseKey = CacheConstant.MESSAGE_GROUP_CACHE +"||"+getMessageDTO.getTargetId();
        } else if (getMessageDTO.getCode() == ImMsgCodeEnum.IM_USER_MSG.getCode()) {
            String mark = Math.max(getMessageDTO.getUserId(), getMessageDTO.getTargetId())+ "and" + Math.min(getMessageDTO.getUserId(),getMessageDTO.getTargetId());
            baseKey = CacheConstant.MESSAGE_USR_CACHE + "||"+mark;
        } else {
            // 或者抛出异常，取决于业务逻辑
            return new ArrayList<>();
        }
        // 确保这里有获取RedisTemplate的方法
        return redisTemplate.opsForList().range(baseKey,0,50);
    }
    //获得会话中最后一条消息
    public ImMsgEntity getLastMessage(GetMessageDTO getMessageDTO){
        String baseKey = null;
        if(getMessageDTO.getCode() == ImMsgCodeEnum.IM_GROUP_MSG.getCode()){
            baseKey = CacheConstant.MESSAGE_GROUP_CACHE +"||"+getMessageDTO.getTargetId();
        } else if (getMessageDTO.getCode() == ImMsgCodeEnum.IM_USER_MSG.getCode()) {
            String mark = Math.max(getMessageDTO.getUserId(), getMessageDTO.getTargetId())+ "and" + Math.min(getMessageDTO.getUserId(),getMessageDTO.getTargetId());
            baseKey = CacheConstant.MESSAGE_USR_CACHE + "||"+mark;
        }
        // 确保这里有获取RedisTemplate的方法
        List<ImMsgEntity> messages = redisTemplate.opsForList().range(baseKey, 0, 50);
// 检查列表是否为空
        // 列表为空的情况，可以根据业务逻辑处理
        // 或者抛出异常，取决于业务逻辑
        if (messages != null && !messages.isEmpty()) {
            // 获取列表中的最后一条消息
            // 处理或返回最后一条消息
            return messages.get(messages.size() - 1); // 或者根据需要进行其他操作
        }
        return null;
    }
}
