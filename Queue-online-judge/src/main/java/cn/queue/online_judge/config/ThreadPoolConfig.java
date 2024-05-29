package cn.queue.online_judge.config;

import cn.queue.online_judge.utils.ThreadPoolUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolUtils getThreadPoolUtils() {
        return new ThreadPoolUtils();
    }
}
