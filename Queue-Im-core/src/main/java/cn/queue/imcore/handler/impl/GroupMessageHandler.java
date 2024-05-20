//package cn.queue.imcore.handler.impl;
//
//import cn.queue.imcore.cache.ChannelHandlerContextCache;
//import cn.queue.imcore.domain.entity.ImMsgEntity;
//import cn.queue.imcore.handler.SimplyHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author: Larry
// * @Date: 2024 /05 /14 / 14:16
// * @Description:群聊包
// */
//@Component
//public class GroupMessageHandler implements SimplyHandler {
//    @Resource
//    private IGroupRepository groupRepository;
//    @Override
//    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
//        Long groupId = imMsg.getTargetId();
//        GroupInfoRich groupInfoRich = groupRepository.getGroupInfo(groupId);
//        if(groupInfoRich!=null){
//            groupInfoRich.getUserList().forEach(
//                    userEntity -> {
//                        sendMessageToUser(userEntity.getId(),imMsg.getContent());
//                    }
//            );
//        }
//
//    }
//    public static void sendMessageToUser(Long userId, String message) {
//        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
//        if (ctx != null && ctx.channel().isActive()) {
//            ctx.writeAndFlush(new TextWebSocketFrame(message));
//        } else {
//            System.out.println("User " + userId + " is not connected or channel is not active.");
//        }
//    }
//}
