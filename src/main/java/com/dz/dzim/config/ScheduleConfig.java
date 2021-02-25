package com.dz.dzim.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author baohan
 * @className 更改定时器线程池初始化数量配置信息
 * @description TODO
 * @date 2021/2/25 16:38
 */

@Configuration
public class ScheduleConfig  {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);
    @Bean(name = "MyThreadPool")
    public Executor MyThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(30);//表示线程池核心线程，正常情况下开启的线程数量。
        executor.setQueueCapacity(500); //配置队列大小
        executor.setMaxPoolSize(50);//当核心线程都在跑任务，还有多余的任务会存到此处。
        executor.setKeepAliveSeconds(60);//非核心线程的超时时长，超长后会被回收。
        executor.setThreadNamePrefix("MyThreadPool-");//配置线程池前缀
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {
            logger.warn("MyThreadPool-当前任务线程池队列已满!");
        });//配置拒绝策略
        executor.initialize();//初始化线程池。
        return executor;
    }

}
