package cn.queue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@ConfigurationProperties(prefix = "rocketmq")
@Configuration
public class RocketMQConsumerProperties {

    private String nameServer;
    private String group;
    private Integer pullBatchSize;
}