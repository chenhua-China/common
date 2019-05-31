package com.chenhua.common.validate.validator;

import cn.hutool.core.util.IdcardUtil;
import com.chenhua.common.validate.IdNum;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdNumValidator implements ConstraintValidator<IdNum, String> {

  private boolean required = true;

  @Override
  public void initialize(IdNum constraintAnnotation) {
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

    return IdcardUtil.isValidCard(value);
  }
}
