//package cn.queue.imcore.handler;
//;
//import cn.queue.cache.ChannelHandlerContextCache;
//import cn.queue.imcore.ImMsg;
//import cn.queue.util.ImContextUtils;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.*;
//import io.netty.channel.group.ChannelGroup;
//import io.netty.channel.group.DefaultChannelGroup;
//import io.netty.handler.codec.http.FullHttpRequest;
//import io.netty.handler.codec.http.HttpRequest;
//import io.netty.handler.codec.http.QueryStringDecoder;
//import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
//import io.netty.handler.codec.http.websocketx.WebSocketFrame;
//import io.netty.util.AttributeKey;
//import io.netty.util.concurrent.GlobalEventExecutor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author: Larry
// * @Date: 2024 /05 /03 / 0:11
// * @Description:
// */
//public class TestWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
//    private Bootstrap bootstrap;
//    private EventLoop eventLoop;
//    private final Logger logger = LoggerFactory.getLogger(TestWebSocketHandler.class);
//    /**
//     * 存储已经登录用户的channel对象
//     */
//    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
//
//
//    /**
//     * 存储用户id和用户的channelId绑定
//     */
//    public static ConcurrentHashMap<Integer, ChannelId> userMap = new ConcurrentHashMap<>();
//    /**
//     * 用于存储群聊房间号和群聊成员的channel信息
//     */
//    public static ConcurrentHashMap<Integer, ChannelGroup> groupMap = new ConcurrentHashMap<>();
//
//    /**
//     * 获取用户拥有的群聊id号
//     */
////    UserGroupRepository userGroupRepositor = SpringUtil.getBean(UserGroupRepository.class);
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("==============================start=================");
//        logger.info("与客户端建立连接，通道开启！");
//
//        channelGroup.add(ctx.channel());
//        ctx.channel().id();
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("与客户端断开连接，通道关闭！");
//        //添加到channelGroup 通道组
//        channelGroup.remove(ctx.channel());
//    }
//
//
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("进来了");
//        if (msg instanceof String) {
//            String message = (String) msg;
//        }
//        if (msg instanceof HttpRequest) {
//            HttpRequest httpRequest = (HttpRequest) msg;
//            // 处理 HTTP 请求
//            System.out.println("Received HTTP request: " + httpRequest.method() + " " + httpRequest.uri());
//            // 如果需要，可以在这里处理 WebSocket 握手逻辑
//        } else if (msg instanceof WebSocketFrame) {
//            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
//            // 处理 WebSocket 消息
//            if (webSocketFrame instanceof TextWebSocketFrame) {
//                Long userId = ImContextUtils.getUserId(ctx);
//                System.out.println(userId);
//                sendMessageToUser(userId,"发送消息成功");
//                TextWebSocketFrame frame = (TextWebSocketFrame) webSocketFrame;
//                System.out.println(frame.text());
//                ImMsg imMsg = JSON.parseObject(frame.text(), ImMsg.class);
//                sendMessageToUser(imMsg.getTargetUserId(), imMsg.getContent());
//                System.out.println(imMsg.getContent());
//            } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
//                BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) webSocketFrame;
//                ByteBuf byteBuf = binaryWebSocketFrame.content();
//                // 处理二进制 WebSocket 消息
//                // 注意：在处理二进制消息时，需要根据实际情况解码消息内容
//            }
//        }
//
//        //首次连接是FullHttpRequest，把用户id和对应的channel对象存储起来
////        if (null != msg && msg instanceof FullHttpRequest) {
////            FullHttpRequest request = (FullHttpRequest) msg;
////            String uri = request.uri();
////            Integer userId = getUrlParams(uri);
////            userMap.put(getUrlParams(uri), ctx.channel().id());
////            logger.info("登录的用户id是：{}", userId);
////            //第1次登录,需要查询下当前用户是否加入过群,加入过群,则放入对应的群聊里
////            List<Integer> groupIds = userGroupRepositor.findGroupIdByUserId(userId);
////            ChannelGroup cGroup = null;
////            //查询用户拥有的组是否已经创建了
////            for (Integer groupId : groupIds) {
////                cGroup = groupMap.get(groupId);
////                //如果群聊管理对象没有创建
////                if (cGroup == null) {
////                    //构建一个channelGroup群聊管理对象然后放入groupMap中
////                    cGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
////                    groupMap.put(groupId, cGroup);
////                }
////                //把用户放到群聊管理对象里去
////                cGroup.add(ctx.channel());
////            }
////            //如果url包含参数，需要处理
////            if (uri.contains("?")) {
////                String newUri = uri.substring(0, uri.indexOf("?"));
////                request.setUri(newUri);
////            }
////
////        } else if (msg instanceof TextWebSocketFrame) {
////            //正常的TEXT消息类型
////            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
////            logger.info("客户端收到服务器数据：{}", frame.text());
////            SocketMessage socketMessage = JSON.parseObject(frame.text(), SocketMessage.class);
////            //处理群聊任务
////            if ("group".equals(socketMessage.getMessageType())) {
////                //推送群聊信息
////                groupMap.get(socketMessage.getChatId()).writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(socketMessage)));
////            } else {
////                //处理私聊的任务，如果对方也在线,则推送消息
////                ChannelId channelId = userMap.get(socketMessage.getChatId());
////                if (channelId != null) {
////                    Channel ct = channelGroup.find(channelId);
////                    if (ct != null) {
////                        ct.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(socketMessage)));
////                    }
////                }
////            }
////        }
//        super.channelRead(ctx, msg);
//    }
//    public static void sendMessageToUser(Long userId, String message) {
//        ChannelHandlerContext ctx = ChannelHandlerContextCache.get(userId);
//        if (ctx != null && ctx.channel().isActive()) {
//            ctx.writeAndFlush(new TextWebSocketFrame(message));
//        } else {
//            System.out.println("User " + userId + " is not connected or channel is not active.");
//        }
//    }
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
//
//    }
//
//
//    private static Integer getUrlParams(String url) {
//        if (!url.contains("=")) {
//            return null;
//        }
//        String userId = url.substring(url.indexOf("=") + 1);
//        return Integer.parseInt(userId);
//    }
//}
