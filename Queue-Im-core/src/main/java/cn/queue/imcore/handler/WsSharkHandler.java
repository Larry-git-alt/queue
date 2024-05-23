package cn.queue.imcore.handler;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.util.ImContextUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author: Larry
 * @Date: 2024 /05 /03 / 0:11
 * @Description:
 */
@Component
@ChannelHandler.Sharable
public class WsSharkHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsSharkHandler.class);

    //指定监听的端口
    private int port = 1010;
    private String serverIp = "8088";
    private WebSocketServerHandshaker webSocketServerHandshaker;
    private static Logger logger = LoggerFactory.getLogger(WsSharkHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //握手接入ws
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
            return;
        }

        //正常关闭链路
        if (msg instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) ((WebSocketFrame) msg).retain());
            return;
        }
        //将消息传递给下一个链路处理器去处理
        ctx.fireChannelRead(msg);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
        String webSocketUrl = "ws://" + serverIp + ":" + port;
        // 构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(webSocketUrl, null, false);
        String uri = msg.uri();
        System.out.println(uri);
        String[] paramArr = uri.split("/");
        Long id = Long.valueOf(paramArr[1]);
        ChannelHandlerContext channelHandlerContext = ChannelHandlerContextCache.get(id);
            //防止异地登陆
            if(channelHandlerContext!=null){
                System.err.println("异地登陆");
                channelHandlerContext.close();
                ImContextUtils.removeUserId(channelHandlerContext);
            }
            ImContextUtils.setUserId(ctx,id);
            ChannelHandlerContextCache.put(id,ctx);
            System.out.println(ChannelHandlerContextCache.get(id));
        //建立ws的握手连接
        webSocketServerHandshaker = wsFactory.newHandshaker(msg);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            return;
        }
        ChannelFuture channelFuture = webSocketServerHandshaker.handshake(ctx.channel(), msg);

    }


}
