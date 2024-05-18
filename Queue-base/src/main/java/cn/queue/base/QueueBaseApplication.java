package cn.queue.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 马兰友
 * @Date: 2024/05/18/13:58
 */
@SpringBootApplication(scanBasePackages = {"cn.queue"} )
@EnableDiscoveryClient
public class QueueBaseApplication {
    private static final Logger LOG = LoggerFactory.getLogger(QueueBaseApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QueueBaseApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("{} 启动成功!!", env.getProperty("spring.application.name"));
        LOG.info("网关地址:\thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}
