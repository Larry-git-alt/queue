package cn.queue.imcore.handler.impl;

import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.common.util.SnowUtil;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.valueObj.TopicConstant;
import cn.queue.imcore.handler.SimplyHandler;
import cn.queue.imcore.service.IMessageService;
import cn.queue.util.ImContextUtils;
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

    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
                System.out.println("test");
                Long userId = ImContextUtils.getUserId(ctx);
                //如果对方在线直接在线发送
                if(userId!=null && ChannelHandlerContextCache.get(imMsg.getTargetId())!=null){
                    sendMessageToUser(imMsg.getTargetId(), imMsg.getContent());
                }
                //否则mq异步写入数据库和mq
                imMsg.setId(SnowUtil.getSnowflakeNextId());
                imMsg.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                messageService.addMessage(imMsg);
//                eventPublisher.publish(TopicConstant.IMAGE_ADD_TOPIC,imMsg);
    }
    public static void sendMessageToUser(Long userId, String message) {
        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
        if (ctx != null && ctx.channel().isActive()) {
            ctx.writeAndFlush(new TextWebSocketFrame(message));
        } else {
            System.out.println("User " + userId + " is not connected or channel is not active.");
        }
    }
}
