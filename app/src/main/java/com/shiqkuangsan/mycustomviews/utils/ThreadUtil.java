package com.shiqkuangsan.mycustomviews.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shiqkuangsan on 2017/1/4.
 * <p>
 * ClassName: ThreadUtil
 * Author: shiqkuangsan
 * Description: 线程管理器,提供一个子线程供外界使用.使用完自动回收到线程池
 * 至于配置
 * 使用:ThreadUtil.getThreadPool().exceute();
 */
public class ThreadUtil {

    private static ThreadPool mThreadPool;

    /**
     * 获取线程池对象
     *
     * @return ThreadPool对象(单例)
     */
    public static ThreadPool getThreadPool() {
        if (mThreadPool == null) {
            synchronized (ThreadUtil.class) {
                if (mThreadPool == null) {
                    // 核心线程数根据CPU核心数确定,最大线程数就直接用核心线程数了,休息时间随便给
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    int threadCount = cpuCount * 4 + 1;
                    MyLogUtil.debug("CPU个数: " + cpuCount);
                    mThreadPool = new ThreadPool(threadCount, threadCount, 1L);
                }
            }
        }
        return mThreadPool;
    }


    private static class ThreadPool {

        int corePoolSize;
        int maximumPoolSize;
        long keepAliveTime;
        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        /**
         * 让线程池执行一个Runnable的方法,其实在里面是创建一个线程池执行器,执行器去做事
         *
         * @param runnable 需要被执行的Runnable对象
         */
        public void exceute(Runnable runnable) {
            if (executor == null)
                /*
                    该构造 参数1: 核心线程数,就是平时工作的线程数   参数2: 最大线程数,可以使用的最大个数    参数3: 线程可以休息的时间
                    参数4: 时间的单位  参数5: 每个线程等待执行任务的队列     参数6: 生产线程的工厂    参数7: 默认的异常处理器
                 */
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            executor.execute(runnable);
        }
    }
}
