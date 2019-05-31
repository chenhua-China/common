package com.chenhua.common.validate.validator;

import com.chenhua.common.validate.UserName;
import com.chenhua.common.validate.ValidPattern;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用户名称规则
 *
 * @author chenhuaping
 * @date 2018年4月17日 下午4:55:48
 */
public class UserNameValidator implements ConstraintValidator<UserName, String> {

  private boolean required = true;

  @Override
  public void initialize(UserName constraintAnnotation) {
    required = constraintAnnotation.required();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    boolean isBlank = StringUtils.isBlank(value);

    if (isBlank && !required) {
      return true;
    }

    if (isBlank) {
      return false;
    }

    return value.matches(ValidPattern.USER_NAME_REG);
  }
}
