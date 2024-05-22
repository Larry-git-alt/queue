package cn.queue.imcore.handler.impl;

import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.common.util.SnowUtil;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.event.BaseEvent;
import cn.queue.imcore.handler.SimplyHandler;

import cn.queue.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import cn.queue.imcore.publisher.EventPublisher;

/**
 * @author: Larry
 * @Date: 2024 /05 /14 / 14:16
 * @Description:私聊包
 */
@Component
public class UserMessageHandler implements SimplyHandler {
    @Resource
    private EventPublisher eventPublisher;

    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
                Long userId = ImContextUtils.getUserId(ctx);
                //如果对方在线直接在线发送
                if(userId!=null && ChannelHandlerContextCache.get(imMsg.getTargetId())!=null){
                    sendMessageToUser(imMsg.getTargetId(), imMsg.getContent());
                }
                //否则mq异步写入数据库
                imMsg.setId(SnowUtil.getSnowflakeNextId());
                BaseEvent<ImMsgEntity> imMsgEntityBaseEvent = new BaseEvent<>();
                imMsgEntityBaseEvent.setData(imMsg);
                eventPublisher.publish("",imMsgEntityBaseEvent);
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
