//package cn.queue.imcore.config;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.redisson.api.redisnode.RedisSingle;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationProperties(prefix = "spring.data.redis") // 注意这里的前缀
//public class RedisConfig {
//    private String address;
//    /**
//     * 单机模式：single 哨兵模式：sentinel 集群模式：cluster
//     */
//    private String mode;
//    /**
//     * 数据库
//     */
//    private Integer database;
//    /**
//     * 密码
//     */
//    private String password;
//    /**
//     * 超时时间
//     */
//    private Integer timeout;
//    /**
//     * 最小空闲数
//     */
//    private Integer poolMinIdle;
//    /**
//     * 连接超时时间(毫秒)
//     */
//    private Integer poolConnTimeout;
//    /**
//     * 连接池大小
//     */
//    private Integer poolSize;
//
//}
