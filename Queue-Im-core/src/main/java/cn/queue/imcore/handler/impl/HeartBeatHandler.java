package cn.queue.imcore.handler.impl;

import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.domain.entity.ImMsgEntity;
import cn.queue.domain.valueObj.ImMsgCodeEnum;
import cn.queue.imcore.handler.SimplyHandler;
import cn.queue.imcore.repository.HeartRepository;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;




/**
 * @author: Larry
 * @Date: 2024 /05 /14 / 14:17
 * @Description: 心跳包
 * 可以获得用户最近活跃的情况
 */
@Component
public class HeartBeatHandler implements SimplyHandler {
    @Resource
    private HeartRepository heartRepository;
    @Override
    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
          System.out.println("检查心跳");
          heartRepository.buildHeartRecord(imMsg.getUserId());
        ImMsgEntity imMsgEntity = ImMsgEntity.builder()
                        .code(ImMsgCodeEnum.IM_HEARTBEAT_MSG.getCode())
                                .content("心跳")
                                        .build();
        sendMessageToUser(imMsg.getUserId(),imMsgEntity);
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
