package cn.queue.imcore.handler.impl;
import cn.queue.imcore.cache.ChannelHandlerContextCache;
import cn.queue.imcore.domain.entity.ImMsgEntity;
import cn.queue.imcore.handler.SimplyHandler;
import cn.queue.imcore.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * @author: Larry
 * @Date: 2024 /05 /14 / 14:16
 * @Description:私聊包
 */
@Component
public class UserMessageHandler implements SimplyHandler {
    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
                Long userId = ImContextUtils.getUserId(ctx);
                //如果对方在线直接在线发送
                if(userId!=null && ChannelHandlerContextCache.get(imMsg.getTargetId())!=null){
                    sendMessageToUser(imMsg.getTargetId(), imMsg.getContent());
                }
                //否则mq异步写入数据库
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
