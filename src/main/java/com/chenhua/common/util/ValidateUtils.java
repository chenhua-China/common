package com.chenhua.common.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 验证
 *
 * @author chenhuaping
 * @date 2018年7月6日 下午8:30:44
 */
public class ValidateUtils {

  /**
   * 检查数据
   *
   * @param obj
   * @return
   */
  public static <T> String validate(T obj) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<T>> violations = validator.validate(obj);

    StringBuilder buider = new StringBuilder();
    if (violations.size() > 0) {
      violations.forEach(
          item -> {
            buider.append(item.getPropertyPath());
            buider.append(":");
            buider.append(item.getMessage());
            buider.append(",");
          });
    }
    if (buider.length() > 0) {
      buider.setLength(buider.length() - 1);
    }
    return buider.toString();
  }
}
