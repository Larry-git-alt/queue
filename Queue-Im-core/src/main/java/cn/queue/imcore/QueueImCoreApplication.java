package cn.queue.imcore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = {"cn.queue"} )
@EnableFeignClients
public class QueueImCoreApplication {

	private static final Logger LOG = LoggerFactory.getLogger(QueueImCoreApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(QueueImCoreApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("{} 启动成功!!", env.getProperty("spring.application.name"));
		LOG.info("网关地址:\thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}

}
