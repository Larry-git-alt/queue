package cn.queue.imcore.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Dominick Li
 * @CreateTime 2020/3/8 16:42
 * @description 容器启动后初始化netty-websocket服务netty
 **/
@Component
public class NettyInitListen implements CommandLineRunner {


    Integer nettyPort = 1010;

    Integer serverPort = 8088;

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("nettyServer starting ...");
            System.out.println("http://127.0.0.1:" + serverPort + "/login");
            new NettyServer(nettyPort).start(nettyPort);
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }
}
