package com.chenhua.common.threadpool.retry.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * 重试执行参数
 *
 * @author chenhuaping
 * @date 2018年4月20日 下午2:54:39
 */
@Setter
@Getter
@ToString
public class RetryInvorkParam implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 延迟时间 */
  private long delayed;

  /** 间隔时间 */
  private long interval;

  /** 重复次数 */
  private int retryTimes;

  /** 执行的方法的对象 */
  private Object target;

  /** 执行的参数 */
  private Object[] args;

  /** 执行的方法 */
  private Method invorkMethod;

  /** 当前执行的次数 */
  private volatile long currentTimes;

  /** 下一次执行的时间 */
  private volatile Date nextInvorkTime;

  /** 是否结束这重试 */
  private volatile boolean isEnd;

  private Future<Void> future;
}
