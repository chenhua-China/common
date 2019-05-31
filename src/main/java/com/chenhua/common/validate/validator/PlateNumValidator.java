package com.chenhua.common.validate.validator;

import com.chenhua.common.validate.PlateNum;
import com.chenhua.common.validate.ValidPattern;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlateNumValidator implements ConstraintValidator<PlateNum, String> {

  private boolean required = true;

  @Override
  public void initialize(PlateNum constraintAnnotation) {
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

    return value.matches(ValidPattern.PLATE_NUM_REG);
  }
}
