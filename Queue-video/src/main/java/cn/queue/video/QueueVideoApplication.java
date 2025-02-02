package cn.queue.video;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = {"cn.queue"} )
@EsMapperScan("cn.queue.video.esmapper")
public class QueueVideoApplication {

    private static final Logger LOG = LoggerFactory.getLogger(QueueVideoApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QueueVideoApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("{} 启动成功!!", env.getProperty("spring.application.name"));
        LOG.info("网关地址:\thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}
