package com.chenhua.common.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配置线程池
 *
 * @author chenhuaping
 * @date 2018年5月31日 下午3:58:36
 */
@Configuration
public class AsynThreadPoolConfig {
  /**
   * 自定义异步线程池
   *
   * @return
   */
  @Bean
  public ThreadPoolTaskScheduler taskExecutor() {

    ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();

    executor.setThreadNamePrefix("Anno-Executor");
    executor.setPoolSize(20);
    //		executor.setMaxPoolSize(50);
    //		executor.setQueueCapacity(20000);
    //		executor.setKeepAliveSeconds(20);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(50);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();

    return executor;
  }
}
