package com.chenhua.common.validate.validator;

import com.chenhua.common.validate.VinNum;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VinNumValidator implements ConstraintValidator<VinNum, String> {

  private boolean required = true;

  @Override
  public void initialize(VinNum constraintAnnotation) {
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
    return CheckVin.isLegal(value);
  }
}
