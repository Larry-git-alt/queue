package cn.queue.imcore.handler.impl;

import cn.hutool.json.JSONUtil;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.common.util.SnowUtil;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.pack.MessageReadContent;
import cn.queue.domain.valueObj.Constants;
import cn.queue.domain.valueObj.TopicConstant;
import cn.queue.imcore.handler.SimplyHandler;
import cn.queue.imcore.service.ConversationService;
import cn.queue.imcore.service.IMessageService;
import cn.queue.util.ImContextUtils;
import cn.queue.util.RedisSequence;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: Larry
 * @Date: 2024 /05 /14 / 14:16
 * @Description:私聊包
 */
@Component
public class UserMessageHandler implements SimplyHandler {
//    @Resource
//    private EventPublisher eventPublisher;
    @Resource
    private IMessageService messageService;
    @Resource
    private ConversationService conversationService;
    @Resource
    private RedisSequence redisSequence;

    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
                System.out.println("test");
                Long userId = ImContextUtils.getUserId(ctx);
                String sequenceKey = 2+"_"+ imMsg.getUserId()+"_"+ Constants.SeqConstants.ConversationSeq;
                imMsg.setSequence(redisSequence.doGetSeq(sequenceKey));
                imMsg.setId(SnowUtil.getSnowflakeNextId());
                //推送给自己发送成功的回执（同时更新messageId和Seq和发送时间）
                if(userId!=null && ChannelHandlerContextCache.get(imMsg.getUserId())!=null){
                    System.err.println("own");
                    System.out.println(imMsg.getUserId());
                    sendMessageToUser(imMsg.getUserId(), imMsg);
                }
                //如果对方在线直接在线发送
                if(userId!=null && ChannelHandlerContextCache.get(imMsg.getTargetId())!=null){
                    System.out.println(imMsg.getTargetId());
                    sendMessageToUser(imMsg.getTargetId(), imMsg);
                }
                //mq异步存储
                MessageReadContent messageReadContent = MessageReadContent.builder()
                        .messageSequence(imMsg.getSequence())
                        .conversationType(2)
                        .fromId(imMsg.getUserId().toString())
                        .toId(imMsg.getTargetId().toString())
                        .build();
                //更新seq(俩会话？)
                conversationService.messageMarkRead(messageReadContent);
                //持久化到数据库和redis
                messageService.addMessage(imMsg);

    }
    public static void sendMessageToUser(Long userId, Object message) {
        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
        if (ctx != null && ctx.channel().isActive()) {
            ctx.writeAndFlush(message);
        } else {
            System.out.println("User " + userId + " is not connected or channel is not active.");
        }
    }
}
