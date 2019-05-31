package com.chenhua.common.validate;

import com.chenhua.common.validate.validator.PlateNumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 车牌号验证验证器 默认必填
 *
 * @author chenhuaping
 * @date 2018年4月17日 下午4:09:38
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PlateNumValidator.class})
public @interface PlateNum {
  String message() default "车牌号不符合规则";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  /**
   * 是否必填
   *
   * @return
   */
  boolean required() default true;
}
