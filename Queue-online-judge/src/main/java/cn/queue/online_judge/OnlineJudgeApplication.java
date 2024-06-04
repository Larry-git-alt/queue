package cn.queue.online_judge;

import cn.queue.common.annnotation.LarryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
@SpringBootApplication(scanBasePackages = {"cn.queue"} )
public class OnlineJudgeApplication {
    private static final Logger LOG = LoggerFactory.getLogger(OnlineJudgeApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OnlineJudgeApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("{} 启动成功!!", env.getProperty("spring.application.name"));
        LOG.info("{} 地址:\thttp://127.0.0.1:{}", env.getProperty("spring.application.name"), env.getProperty("server.port"));
        System.out.println("fkfsb");
    }
}
