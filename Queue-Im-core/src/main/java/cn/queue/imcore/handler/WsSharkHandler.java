package cn.queue.imcore.handler;
import cn.queue.cache.ChannelHandlerContextCache;
import cn.queue.domain.vo.SessionVO;
import cn.queue.imcore.repository.SessionRepository;
import cn.queue.util.ImContextUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Larry
 * @Date: 2024 /05 /03 / 0:11
 * @Description:
 */
@Component
@ChannelHandler.Sharable
public class WsSharkHandler extends ChannelInboundHandlerAdapter {
    @Resource
    private SessionRepository sessionRepository;
    private static WsSharkHandler wsSharkHandler;

    public void setSessionRepository(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    @PostConstruct     //关键二   通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    public void init() {
         wsSharkHandler =this;
         wsSharkHandler.sessionRepository =sessionRepository;// 初使化时将已静态化的testService实例化
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WsSharkHandler.class);

    //指定监听的端口
    private int port = 1010;
    private String serverIp = "8088";
    private WebSocketServerHandshaker webSocketServerHandshaker;
    private static Logger logger = LoggerFactory.getLogger(WsSharkHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
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

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) throws InterruptedException {
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
        System.out.println("test1");
        ChannelFuture channelFuture = webSocketServerHandshaker.handshake(ctx.channel(), msg);
        System.out.println("test2");

    }

//    private void OnlineNotice(Long userId){
//        List<SessionVO> sessionVOList = wsSharkHandler.sessionRepository.getSessionVOList(userId);
//        sessionVOList.forEach(
//                sessionVO -> sendMessageToUser(sessionVO.getToId(),userId+"上线了")
//        );
//    }
    public static void sendMessageToUser(Long userId, String message) {
        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
        if (ctx != null && ctx.channel().isActive()) {
            ctx.writeAndFlush(new TextWebSocketFrame(message));
        } else {
            System.out.println("User " + userId + " is not connected or channel is not active.");
        }
    }

}
