package com.chenhua.common.util.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface ExcelCell {

  /**
   * 表头
   *
   * @return
   */
  String title() default "";

  /**
   * 宽度
   *
   * @return
   */
  int width() default 10;

  /**
   * 排序号
   *
   * @return
   */
  int sort() default 100;

  /**
   * 数字(0.00)和时间格式的格式化(yyyy-MM-dd HH:mm:ss)
   *
   * @return
   */
  String format() default "";
}
