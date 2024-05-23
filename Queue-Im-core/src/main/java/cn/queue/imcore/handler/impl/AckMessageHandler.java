package cn.queue.imcore.handler.impl;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.common.util.SnowUtil;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.event.BaseEvent;
import cn.queue.domain.valueObj.TopicConstant;
import cn.queue.imcore.handler.SimplyHandler;
import cn.queue.imcore.service.IMessageService;
import cn.queue.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.SimpleAssociationHandler;
import org.springframework.stereotype.Component;

/**
 * @author: Larry
 * @Date: 2024 /05 /22 / 10:35
 * @Description: ack消息确认类
 */
@Component
public class AckMessageHandler implements SimplyHandler {

    @Resource
    private IMessageService messageService;
    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
        Long userId = ImContextUtils.getUserId(ctx);
        //直接向对方返回消息包
        if(userId!=null && ChannelHandlerContextCache.get(imMsg.getTargetId())!=null){
            sendMessageToUser(imMsg.getTargetId(), imMsg.getContent());

            //异步更新数据库消息状态
        }
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
