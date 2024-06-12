//package cn.queue.imcore.config;
//
//
//import org.redisson.api.RedissonClient;
//
///**
// * Redisson 客户端管理类
// * @author BanTanger 半糖
// * @Date 2023/3/24 22:07
// */
//public class RedissonManager {
//
//    private static RedissonClient redissonClient;
//
//    public static void init(RedisConfig redisConfig) {
//        SingleClientStrategy singleClientStrategy = new SingleClientStrategy();
//        redissonClient = singleClientStrategy.getRedissonClient(redisConfig);
//    }
//
//    public static RedissonClient getRedissonClient() {
//        return redissonClient;
//    }
//
//}
