package com.chenhua.common.format;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化器
 *
 * @author chenhuaping
 * @date 2018年4月19日 下午6:43:04
 */
public class DateFormatSerializeFilter implements ContextValueFilter {

  private static final Logger logger = LoggerFactory.getLogger(DateFormatSerializeFilter.class);

  @SuppressWarnings("unused")
  @Override
  public Object process(BeanContext context, Object object, String name, Object value) {

    if (value == null || !(value instanceof Date) || context == null) {
      return value;
    }

    Timestamp annation = context.getAnnation(Timestamp.class);
    DateFormat dateFormat = context.getAnnation(DateFormat.class);

    if (annation == null || dateFormat == null) {
      return value;
    }

    if (annation != null) {
      return ((Date) value).getTime();
    }

    if (dateFormat != null) {
      SimpleDateFormat format = new SimpleDateFormat(dateFormat.formatter());
      return format.format(value);
    }
    return value;
  }
}
