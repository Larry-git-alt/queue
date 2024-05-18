package cn.queue.forum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication(scanBasePackages = {"cn.queue"} )
public class QueueFourmApplication {

	private static final Logger LOG = LoggerFactory.getLogger(QueueFourmApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(QueueFourmApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("{} 启动成功!!", env.getProperty("spring.application.name"));
		LOG.info("网关地址:\thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}
}
