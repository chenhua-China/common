package com.chenhua.common.threadpool.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

  /**
   * 延时执行时间 （单位：秒）
   *
   * @return
   */
  long delayed() default 0;

  /**
   * 重复执行间隔时间 （单位：秒）
   *
   * @return
   */
  long interval() default 0;

  /**
   * 重复的次数 最大10次
   *
   * @return
   */
  int retryTimes() default 1;
}
