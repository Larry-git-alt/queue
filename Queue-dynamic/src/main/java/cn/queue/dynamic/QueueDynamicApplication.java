package cn.queue.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

/**
 * @author 马兰友
 * @Date: 2024/05/18/13:58
 */
@SpringBootApplication
@EnableDiscoveryClient
public class QueueDynamicApplication {
    private static final Logger LOG = LoggerFactory.getLogger(QueueDynamicApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QueueDynamicApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("{} 启动成功!!", env.getProperty("spring.application.name"));
        LOG.info("网关地址:\thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}
