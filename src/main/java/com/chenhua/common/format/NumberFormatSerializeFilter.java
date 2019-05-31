package com.chenhua.common.format;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字格式化器
 *
 * @author chenhuaping
 * @date 2018年4月19日 下午6:43:04
 */
public class NumberFormatSerializeFilter implements ContextValueFilter {

  private static final Logger logger = LoggerFactory.getLogger(NumberFormatSerializeFilter.class);

  @Override
  public Object process(BeanContext context, Object object, String name, Object value) {
    if (value == null
        || !(value instanceof BigDecimal)
        || context == null
        || !(value instanceof Double)) {
      return value;
    }
    NumberFormat numberFormat = context.getAnnation(NumberFormat.class);
    Number num = (Number) value;
    DecimalFormat nf = new DecimalFormat(numberFormat.formatter());
    String format = nf.format(num);
    return Double.parseDouble(format);
  }
}
