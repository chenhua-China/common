package com.chenhua.common.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.chenhua.common.util.anon.SignField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignFieldUtils {

  public static final Class[] ALLOW_TYPE = {
    Byte.class,
    Character.class,
    Short.class,
    Integer.class,
    Long.class,
    Float.class,
    Double.class,
    BigInteger.class,
    BigDecimal.class,
    Boolean.class,
    String.class,
    Date.class,
    java.sql.Date.class,
    java.sql.Timestamp.class,
    byte.class,
    char.class,
    short.class,
    int.class,
    long.class,
    float.class,
    double.class,
    boolean.class
  };

  /**
   * 获取有@SignField标志的属性
   *
   * @param obj
   * @return
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public static Map<String, Object> getSignFieldObj(Object obj)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

    Map<String, Object> map = new HashMap<>();

    if (obj == null || obj.getClass() == Object.class) {
      return null;
    }

    if (obj.getClass().isArray()) {
      return null;
    }
    if (!isAllowBaseType(obj.getClass())) {
      for (Class<?> superClass = obj.getClass();
          superClass != Object.class;
          superClass = superClass.getSuperclass()) {
        Field[] field = superClass.getDeclaredFields();
        for (Field f : field) {
          Reflections.makeAccessible(f);
          Object o = f.get(obj);
          if (!isAllowBaseType(f.getType())) {
            Map<String, Object> signFieldObjMap = getSignFieldObj(o);
            if (signFieldObjMap != null && signFieldObjMap.size() > 0) {
              map.putAll(signFieldObjMap);
            }
          }
          if (f.getDeclaredAnnotation(SignField.class) != null) {
            map.put(getFieldKeyName(f), o);
          }
        }
      }
    }
    return map;
  }

  /** @return */
  public static boolean isAllowBaseType(Class clazz) {
    for (Class clz : ALLOW_TYPE) {
      if (clz.equals(clazz)) {
        return true;
      }
    }
    return false;
  }

  private static String getFieldKeyName(Field field) {

    JsonProperty jsonAnon = field.getDeclaredAnnotation(JsonProperty.class);
    if (jsonAnon != null) {
      return jsonAnon.value();
    }

    JSONField jsonField = field.getDeclaredAnnotation(JSONField.class);
    if (jsonField != null) {
      return jsonField.name();
    }

    return field.getName();
  }

  private String getMethodKeyName(Method method) {
    JsonProperty jsonAnon = method.getDeclaredAnnotation(JsonProperty.class);
    if (jsonAnon != null) {
      return jsonAnon.value();
    }

    JSONField jsonField = method.getDeclaredAnnotation(JSONField.class);
    if (jsonField != null) {
      return jsonField.name();
    }
    String methodName = method.getName();
    String filedName =
        methodName.substring(3, 4).toLowerCase()
            + (methodName.length() > 4 ? methodName.substring(4) : "");
    return filedName;
  }
}
