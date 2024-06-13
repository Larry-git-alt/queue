package cn.queue.file.config;

import cn.queue.file.utils.MinIOUtils;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 *
 * @author queue
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig
{
    /**
     * 服务地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucketName;

    private Integer imgSize;

    private Integer fileSize;


    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public MinIOUtils creatMinioClient() {
        return new MinIOUtils(url, bucketName, accessKey, secretKey, imgSize, fileSize);
    }


}
