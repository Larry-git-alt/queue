package cn.queue.online_judge.utils;

import java.util.concurrent.*;

public class ThreadPoolUtils {

    public ThreadPoolUtils() {

    }

    /**
     * 线程池核心线程数
     */
    private int corePoolSize = 5;
    /**
     * 线程池最大线程数
     */
    private int maxPoolSize = 10;
    /**
     * 非核心线程最大空闲时间
     */
    private long keepAliveTime = 1;
    /**
     * 空闲时间单位
     */
    private TimeUnit unit = TimeUnit.MINUTES;

    /**
     * 阻塞队列
     */
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
    /**
     * 线程池工厂
     */
    private ThreadFactory threadFactory = Executors.defaultThreadFactory();
    /**
     * 异常捕获
     */
    private RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

    /**
     * 创建线程池
     */
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue);

    public void execute(Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }
}
