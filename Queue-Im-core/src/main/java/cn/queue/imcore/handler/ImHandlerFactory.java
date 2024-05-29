package cn.queue.imcore.handler;
import cn.queue.domain.entity.ImMsgEntity;
import io.netty.channel.ChannelHandlerContext;


/**
 * @Author idea
 * @Date: Created in 20:42 2023/7/6
 * @Description
 */
public interface ImHandlerFactory {


    /**
     * 按照immsg的code去筛选
     *
     * @param channelHandlerContext
     * @param imMsg
     */
    void doMsgHandler(ChannelHandlerContext channelHandlerContext, ImMsgEntity imMsg);
    String test();
}
