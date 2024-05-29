package cn.queue.imcore.encoder;

import cn.queue.domain.entity.ImMsgEntity;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author: Larry
 * @Date: 2024 /05 /03 / 0:11
 * @Description:
 */
public class WebsocketEncoder extends ChannelOutboundHandlerAdapter  {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof ImMsgEntity)) {
            super.write(ctx, msg, promise);
            return;
        }
        ImMsgEntity imMsgEntity = (ImMsgEntity) msg;
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(imMsgEntity)));
    }
}
