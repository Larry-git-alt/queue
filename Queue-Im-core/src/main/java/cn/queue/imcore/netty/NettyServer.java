//package cn.queue.imcore.netty;
//
//import cn.queue.imcore.encoder.WebsocketEncoder;
//import cn.queue.imcore.handler.WebSocketHandler;
//import cn.queue.imcore.handler.WsSharkHandler;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import lombok.Data;
//import org.springframework.context.ConfigurableApplicationContext;
//
///**
// * @author: Larry
// * @Date: 2024 /04 /29 / 16:30
// * @Description:
// */
//@Data
//public class NettyServer {
//    //指定监听的端口
//    private int port;
//
//    public NettyServer(int port) {
//        this.port = port;
//    }
//    public void start(int port) {
//        //配置服务端NIO线程组
//        EventLoopGroup bossGroup = new NioEventLoopGroup(); //NioEventLoopGroup extends MultithreadEventLoopGroup Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            System.out.println("netty 初始化");
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)    //非阻塞模式
//                    .option(ChannelOption.SO_BACKLOG, 128)
//                    //保活开关2h没有数据服务端会发送心跳包
//                    .childHandler(new ChannelInitializer() {
//                @Override
//                protected void initChannel(Channel ch) throws Exception {
//                    //打印日志，方便观察
//                    //因为基于http协议 使用http的编码和解码器
//                    ch.pipeline().addLast(new HttpServerCodec());
//                    //是以块方式写 添加处理器
//                    ch.pipeline().addLast(new ChunkedWriteHandler());
//                    //http数据在传输过程中是分段 就是可以将多个段聚合 这就是为什么当浏览器发生大量数据时 就会发生多次http请求
//                    ch.pipeline().addLast(new HttpObjectAggregator(8192));
//                    ch.pipeline().addLast(new WebsocketEncoder());
//                    ch.pipeline().addLast(new WsSharkHandler());
//                    ch.pipeline().addLast(new WebSocketHandler());
////                    ch.pipeline().addLast(
////                            new IdleStateHandler(0, 5, 0, TimeUnit.SECONDS),new NettyClientHandler()
////                    );
//                    ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10));
//                }
//            });
//            //基于钩子函数优雅关闭
//            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//                bossGroup.shutdownGracefully();
//                workerGroup.shutdownGracefully();
//            }));
//            ChannelFuture f = b.bind(port).sync();
//            f.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//
//    }
//
//
//
//}
