package com.chenhua.common.validate.validator;

import com.chenhua.common.validate.Mobile;
import com.chenhua.common.validate.ValidPattern;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

  private boolean required = true;

  @Override
  public void initialize(Mobile constraintAnnotation) {
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

    return value.matches(ValidPattern.PHONE_NUMBER_REG);
  }
}
