package com.chenhua.common.threadpool.retry;

import com.chenhua.common.threadpool.retry.model.RetryInvorkParam;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 方法执行任务
 *
 * @author chenhuaping
 * @date 2018年4月20日 下午3:54:13
 */
public class RetryRunning implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(RetryRunning.class);
  /** 执行的参数 */
  private RetryInvorkParam invorkParam;

  public RetryRunning(RetryInvorkParam invorkParam) {
    super();
    this.invorkParam = invorkParam;
  }

  @Override
  public void run() {

    if (invorkParam.isEnd()) {
      invorkParam.getFuture().cancel(false);
      return;
    }

    Method invorkMethod = invorkParam.getInvorkMethod();
    Object[] args = invorkParam.getArgs();
    Object target = invorkParam.getTarget();

    Object result = null;

    // 执行时间
    Date now = new Date();
    Date nextInvorkTime = invorkParam.getNextInvorkTime();

    // 增加执行次数
    long currentTimes = invorkParam.getCurrentTimes();
    currentTimes++;
    invorkParam.setCurrentTimes(currentTimes);
    // 判断是否为最后一次
    if (currentTimes == invorkParam.getRetryTimes()) {
      invorkParam.setEnd(true);
      invorkParam.setNextInvorkTime(null);
    } else {
      // 计算下一次执行时间；
      nextInvorkTime =
          DateUtils.addSeconds(nextInvorkTime, Long.valueOf(invorkParam.getInterval()).intValue());
      invorkParam.setNextInvorkTime(nextInvorkTime);
    }

    try {
      result = invorkMethod.invoke(target, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      logger.error("@Retry 执行方法错误, {}", invorkParam, e);
      invorkParam.setEnd(true);
    }

    if (result != null && result instanceof Boolean && (boolean) result == true) {
      invorkParam.setEnd(true);
    }

    if (invorkParam.isEnd()) {
      logger.info(
          "@Retry method = {}, 重复执行了{}次，已经执行结束，param = {}",
          target.getClass() + "." + invorkMethod.getName(),
          invorkParam.getCurrentTimes(),
          invorkParam);
      invorkParam.getFuture().cancel(false);
      return;
    }

    logger.info(
        "@Retry 已经完成了{}方法，第{}次执行，总共需要执行{}次",
        target.getClass() + "." + invorkMethod.getName(),
        invorkParam.getCurrentTimes(),
        invorkParam.getRetryTimes());
  }
}
