package cn.queue.imcore.handler.impl;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.common.domain.entity.UserEntity;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.imcore.handler.SimplyHandler;
import cn.queue.imcore.service.IGroupService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author: Larry
 * @Date: 2024 /05 /14 / 14:16
 * @Description:群聊包
 */
@Component
public class GroupMessageHandler implements SimplyHandler {
    @Resource
    private IGroupService groupService;
    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
        Long groupId = imMsg.getTargetId();
        List<UserEntity> userByGroupId = groupService.getUserByGroupId(groupId);
        userByGroupId.forEach(
                userEntity -> sendMessageToUser(userEntity.getUserId(), imMsg.getContent())
                //异步存储
        );
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
