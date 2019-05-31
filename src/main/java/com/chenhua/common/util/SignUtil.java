package com.chenhua.common.util;

import com.chenhua.common.util.encrypt.MD5;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 签名工具类
 *
 * <p>SignUtil<br>
 * 创建人:chenhuaping <br>
 * 时间：2018年6月28日-下午4:36:13 <br>
 *
 * @version 2.0
 */
public class SignUtil {

  /**
   * 签名<br>
   * 方法名：sign<br>
   * 创建人：wangbeidou <br>
   * 时间：2018年6月28日-下午4:36:31 <br>
   *
   * @param parameters
   * @return String<br>
   * @exception <br>
   * @since 2.0
   */
  public static String sign(Map<String, Object> parameters) {
    String result = getSortStr(parameters);
    return MD5.EncoderByMd5(result);
  }

  /**
   * 签名<br>
   * 方法名：sign<br>
   * 创建人：wangbeidou <br>
   * 时间：2018年6月28日-下午4:36:31 <br>
   *
   * @param parameters
   * @return String<br>
   * @exception <br>
   * @since 2.0
   */
  public static String getSortStr(Map<String, Object> parameters) {
    Map<String, Object> sortedmap = getSortedData(parameters);
    StringBuffer bs = new StringBuffer();
    for (Entry<String, Object> o : sortedmap.entrySet()) {
      if (!"sign".equals(o.getKey())) {
        bs.append(o.getKey() + "=" + o.getValue() + "&");
      }
    }
    String result = bs.deleteCharAt(bs.length() - 1).toString();
    return result;
  }

  public static Map<String, Object> getSortedData(Map<String, Object> map) {
    if (map == null) {
      return null;
    }

    Map<String, Object> m = new TreeMap<String, Object>();
    for (Entry<String, Object> o : map.entrySet()) {
      m.put(o.getKey(), o.getValue());
    }
    return m;
  }
}
