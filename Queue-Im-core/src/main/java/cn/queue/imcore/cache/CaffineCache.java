package cn.queue.imcore.cache;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cglib.core.internal.LoadingCache;
import javax.cache.integration.CacheLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: Larry
 * @Date: 2024 /06 /05 / 19:15
 * @Description:
 */
public class CaffineCache {
    public static void demo(){
        Cache<String,String> cache = Caffeine.newBuilder()
                .expireAfterWrite(20, TimeUnit.SECONDS)
                .maximumSize(5000)
                .build();

        // 1.Insert or update an entry
        cache.put("hello","world");

        // 2. Lookup an entry, or null if not found
        String val1 = cache.getIfPresent("hello");
        System.out.println(val1);

        // 3. Lookup and compute an entry if absent, or null if not computable
        cache.get("msg", CaffineCache::createExpensiveGraph);

        // 4. Remove an entry
        cache.invalidate("hello");
    }

    private static String createExpensiveGraph(String key){
        System.out.println("begin to query db..."+Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        System.out.println("success to query db...");
        return UUID.randomUUID().toString();
    }



}
