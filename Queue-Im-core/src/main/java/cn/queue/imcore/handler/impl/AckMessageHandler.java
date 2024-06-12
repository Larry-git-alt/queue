package cn.queue.imcore.handler.impl;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.pack.MessageReadContent;
import cn.queue.domain.valueObj.TopicConstant;
import cn.queue.imcore.handler.SimplyHandler;
import cn.queue.imcore.publisher.AckPublish;
import cn.queue.imcore.service.ConversationService;
import cn.queue.imcore.service.IMessageService;
import cn.queue.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
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
    @Resource
    private ConversationService conversationService;
    @Resource
    private AckPublish ackPublish;
    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
        Long userId = ImContextUtils.getUserId(ctx);
        //给对方发送ack消息
        if(userId!=null && ChannelHandlerContextCache.get(imMsg.getTargetId())!=null){
            sendMessageToUser(imMsg.getTargetId(),imMsg);
        }
        MessageReadContent messageReadContent = MessageReadContent.
                builder()
                .fromId(imMsg.getUserId().toString())
                .toId(imMsg.getTargetId().toString())
                .conversationType(2)
                .messageSequence(22)
                .build();
        ackPublish.publish(TopicConstant.IMAGE_ACK_TOPIC,messageReadContent);
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
