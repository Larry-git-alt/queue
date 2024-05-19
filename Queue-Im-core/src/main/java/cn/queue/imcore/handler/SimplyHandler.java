package cn.queue.imcore.handler;


import cn.queue.imcore.domain.entity.ImMsgEntity;
import io.netty.channel.ChannelHandlerContext;


/**
 * @Author idea
 * @Date: Created in 20:39 2023/7/6
 * @Description
 */
public interface SimplyHandler {

    /**
     * 消息处理函数
     *
     * @param ctx
     * @param imMsg
     */
    void handler(ChannelHandlerContext ctx, ImMsgEntity imMsg);
}
