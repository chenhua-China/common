package com.chenhua.common.format;

import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.chenhua.common.validate.ValidPattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 时间格式化器
 *
 * @author chenhuaping
 * @date 2018年4月19日 下午6:43:04
 */
public class HideSensitiveFormatSerializeFilter implements ContextValueFilter {

  private static final Logger logger =
      LoggerFactory.getLogger(HideSensitiveFormatSerializeFilter.class);

  @SuppressWarnings("unused")
  @Override
  public Object process(BeanContext context, Object object, String name, Object value) {

    if (value == null || !(value instanceof String) || context == null) {
      return value;
    }

    String val = (String) value;
    if (StringUtils.isBlank(val)) {
      return val;
    }
    val = val.trim();

    HideSensitiveFormat annation = context.getAnnation(HideSensitiveFormat.class);
    if (annation == null) {
      return value;
    }

    // 判断是手机号
    if (val.matches(ValidPattern.PHONE_NUMBER_REG)) {
      return StringUtils.rightPad(val.substring(0, 3), val.length() - 4, "*")
          + val.substring(val.length() - 4);
    }

    // 判断是否为身份证号
    if ((val.length() == 18 || val.length() == 15) && IdcardUtil.isValidCard(val)) {
      return StringUtils.rightPad(val.substring(0, 6), val.length() - 4, "*")
          + val.substring(val.length() - 4);
    }

    // 判断是否为银行卡号
    if (val.matches("\\d{12,19}")) {
      return StringUtils.rightPad(val.substring(0, 4), val.length() - 4, "*")
          + val.substring(val.length() - 4);
    }

    // 显示前一个字符和最后一个字符
    if (val.length() > 2) {
      return StringUtils.rightPad(val.substring(0, 1), val.length() - 1, "*")
          + val.substring(val.length() - 1);
    } else if (val.length() == 2) {
      return "*" + val.substring(1);
    } else {
      return val;
    }
  }
}
