package com.chenhua.common.threadpool.retry;

import com.chenhua.common.threadpool.retry.model.RetryInvorkParam;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

/**
 * 线程提交执行引擎
 *
 * @author chenhuaping
 * @date 2018年4月20日 下午4:22:00
 */
@Component
public class RetryScheduledEngine extends TimerTask implements InitializingBean {

  private static final Logger logger = LoggerFactory.getLogger(RetryScheduledEngine.class);

  //  private static final List<RetryInvorkParam> invorkParamList = new LinkedList<>();

  @Autowired(required = false)
  private ThreadPoolTaskScheduler threadPoolTaskScheduler;

  //  /** 开始任务的 */
  //  private synchronized void startEngine() {
  //    if (CollectionUtil.isNotEmpty(invorkParamList)) {
  //      Iterator<RetryInvorkParam> iterator = invorkParamList.iterator();
  //      while (iterator.hasNext()) {
  //        RetryInvorkParam next = iterator.next();
  //        if (next.isEnd()) {
  //          iterator.remove();
  //          continue;
  //        }
  //        // 执行时间
  //        Date now = new Date();
  //        Date nextInvorkTime = next.getNextInvorkTime();
  //
  //        if (nextInvorkTime != null && nextInvorkTime.before(now)) {
  //          // 增加执行次数
  //          long currentTimes = next.getCurrentTimes();
  //          currentTimes++;
  //          next.setCurrentTimes(currentTimes);
  //          // 判断是否为最后一次
  //          if (currentTimes == next.getRetryTimes()) {
  //            next.setEnd(true);
  //          }
  //
  //          // 计算下一次执行时间；
  //          nextInvorkTime =
  //              DateUtils.addSeconds(nextInvorkTime, Long.valueOf(next.getInterval()).intValue());
  //          next.setNextInvorkTime(nextInvorkTime);
  //
  //          // 添加任务
  //          threadPoolTaskScheduler.submit(new RetryRunning(next));
  //        }
  //      }
  //    }
  //  }

  /**
   * 提交任务
   *
   * @param param
   */
  public void submitTask(RetryInvorkParam param) {
    // 计算执行时间
    Date now = new Date();
    if (param.getDelayed() > 0) {
      param.setNextInvorkTime(
          DateUtils.addSeconds(now, Long.valueOf(param.getDelayed()).intValue()));
    } else {
      param.setNextInvorkTime(now);
    }
    ScheduledFuture scheduledFuture =
        threadPoolTaskScheduler.scheduleWithFixedDelay(
            new RetryRunning(param), param.getNextInvorkTime(), param.getInterval() * 1000);
    param.setFuture(scheduledFuture);
  }

  @Override
  public void run() {
    try {
      //      this.startEngine();
    } catch (Exception e) {
      logger.error("执行重试定时任务错误", e);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    //    threadPoolTaskScheduler.scheduleWithFixedDelay(this, DateUtils.addSeconds(new Date(), 5),
    // 1000);
  }
  /** 测试方法 */
  @Retry(delayed = 5, interval = 2, retryTimes = 10)
  public boolean testRetryInvoke() {
    System.out.println("______________testRetryInvoke被执行_________________");
    return false;
  }
}
