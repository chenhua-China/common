package com.chenhua.common.validate;

/**
 * 正则表达式
 *
 * @author chenhuaping
 * @date 2018年4月17日 下午4:46:01
 */
public class ValidPattern {

  /** 手机规则正则表达式 */
  public static final String PHONE_NUMBER_REG =
      "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

  /** 用户名称规则 */
  public static final String USER_NAME_REG = "[\\u4E00-\\u9FA5]{2,5}(?:·[\\u4E00-\\u9FA5]{2,5})*";

  /** 车牌正则验证 */
  public static final String PLATE_NUM_REG =
      "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";

  public static void main(String[] args) {
    System.out.println("冀A7879A".matches(PLATE_NUM_REG));
  }
}
