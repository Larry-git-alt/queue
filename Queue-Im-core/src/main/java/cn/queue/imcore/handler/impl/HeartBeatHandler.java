//package cn.queue.imcore.handler.impl;
//
//
//import cn.queue.imcore.domain.entity.ImMsgEntity;
//import cn.queue.imcore.handler.SimplyHandler;
//import io.netty.channel.ChannelHandlerContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//
///**
// * @author: Larry
// * @Date: 2024 /05 /14 / 14:17
// * @Description: 心跳包
// * 可以获得用户最近活跃的情况
// */
//@Component
//public class HeartBeatHandler implements SimplyHandler {
//    @Resource
//    private IHeartRepository heartRepository;
//    @Override
//    public void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg) {
//          System.out.println("检查心跳");
//          heartRepository.buildHeartRecord(imMsg.getUserId());
//          heartRepository.removeExpireRecord(imMsg.getUserId());
//    }
//}
