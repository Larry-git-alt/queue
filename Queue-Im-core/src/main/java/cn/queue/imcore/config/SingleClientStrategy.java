//package cn.queue.imcore.config;
//import jakarta.annotation.Resource;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.client.codec.StringCodec;
//import org.redisson.config.Config;
//import org.redisson.config.SingleServerConfig;
//
///**
// * @author BanTanger 半糖
// * @Date 2023/3/24 22:08
// */
//public class SingleClientStrategy {
//    @Resource
//    RedisConfig redisConfig;
//
//    public RedissonClient getRedissonClient() {
//        Config config = new Config();
//        String node = redisConfig.getAddress();
//        node = node.startsWith("redis://") ? node : "redis://" + node;
//        SingleServerConfig serverConfig = config.useSingleServer()
//                .setAddress(node)
//                .setDatabase(redisConfig.getDatabase())
//                .setTimeout(redisConfig.getTimeout())
//                .setConnectionMinimumIdleSize(redisConfig.getPoolMinIdle())
//                .setConnectTimeout(redisConfig.getPoolConnTimeout())
//                .setConnectionPoolSize(redisConfig.getPoolSize());
//        if (StringUtils.isNotBlank(redisConfig.getPassword())) {
//            serverConfig.setPassword(redisConfig.getPassword());
//        }
//        StringCodec stringCodec = new StringCodec();
//        config.setCodec(stringCodec);
//        return Redisson.create(config);
//    }
//
//}
