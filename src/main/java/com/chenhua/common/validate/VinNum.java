package com.chenhua.common.validate;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.chenhua.common.validate.validator.VinNumValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 身份证验证器 默认必填
 *
 * @author chenhuaping
 * @date 2018年4月17日 下午4:09:38
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {VinNumValidator.class})
public @interface VinNum {
  String message() default "车架号不符合规则";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
  /**
   * 是否必填
   *
   * @return
   */
  boolean required() default true;
}
