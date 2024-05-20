package cn.queue.imcore.handler;
import cn.queue.imcore.domain.entity.ImMsgEntity;
import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
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
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Resource
    private ImHandlerFactory imHandlerFactory;
    private static WebSocketHandler webSocketHandler;

    public void setImHandlerFactory(ImHandlerFactory imHandlerFactory) {
         this.imHandlerFactory = imHandlerFactory;
    }
    @PostConstruct     //关键二   通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    public void init() {
        webSocketHandler = this;
        webSocketHandler.imHandlerFactory = imHandlerFactory;   // 初使化时将已静态化的testService实例化
    }
    private Bootstrap bootstrap;
    private EventLoop eventLoop;
    private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);
    /**
     * 存储已经登录用户的channel对象
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("==============================start=================");
        logger.info("与客户端建立连接，通道开启！");
        channelGroup.add(ctx.channel());
        ctx.channel().id();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("与客户端断开连接，通道关闭！");
        //添加到channelGroup 通道组
        channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            // 处理 HTTP 请求
            System.out.println("Received HTTP request: " + httpRequest.method() + " " + httpRequest.uri());
            // 如果需要，可以在这里处理 WebSocket 握手逻辑
        } else if (msg instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
            // 处理 WebSocket 消息
            if (webSocketFrame instanceof TextWebSocketFrame) {
                TextWebSocketFrame frame = (TextWebSocketFrame) webSocketFrame;
                System.out.println(frame.text());
                ImMsgEntity imMsgEntity = JSON.parseObject(frame.text(), ImMsgEntity.class);
                webSocketHandler.imHandlerFactory.doMsgHandler(ctx,imMsgEntity);
            } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) webSocketFrame;
                ByteBuf byteBuf = binaryWebSocketFrame.content();
                // 处理二进制 WebSocket 消息
                // 注意：在处理二进制消息时，需要根据实际情况解码消息内容
            }
        }

        super.channelRead(ctx, msg);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    private static Integer getUrlParams(String url) {
        if (!url.contains("=")) {
            return null;
        }
        String userId = url.substring(url.indexOf("=") + 1);
        return Integer.parseInt(userId);
    }
}
