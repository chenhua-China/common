package com.chenhua.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

  public static final String DEFAULT_CODETYPE = "UTF-8";

  public static String SHA1(String decript) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      digest.update(decript.getBytes());
      byte messageDigest[] = digest.digest();
      // Create Hex String
      StringBuffer hexString = new StringBuffer();
      // 字节数组转换为 十六进制 数
      for (int i = 0; i < messageDigest.length; i++) {
        String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
        if (shaHex.length() < 2) {
          hexString.append(0);
        }
        hexString.append(shaHex);
      }
      return hexString.toString();

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String shaEncode(String inputStr) {
    MessageDigest sha = null;
    try {
      sha = MessageDigest.getInstance("SHA");
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
      return "";
    }
    byte[] byteArray;
    try {
      byteArray = inputStr.getBytes(DEFAULT_CODETYPE);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return "";
    }
    byte[] md5Bytes = sha.digest(byteArray);
    StringBuffer hexValue = new StringBuffer();
    for (int i = 0; i < md5Bytes.length; i++) {
      int val = ((int) md5Bytes[i]) & 0xff;
      if (val < 16) {
        hexValue.append("0");
      }
      hexValue.append(Integer.toHexString(val));
    }
    return hexValue.toString();
  }

  public static void main(String[] args) {
    String str =
        "jsapi_ticket=HoagFKDcsGMVCIY2vOjf9nK6AaCkALuLwYnyYOh9dLjNiwp8G4GPkMJ0NoBN03pd6RdcniPuy-1IlGw0SH1mF&noncestr=50f8481c2ccd4f32&timestamp=153001205&url=http://www.baidu.com";
    String sha1 = EncryptUtil.SHA1(str);
    System.out.println(sha1);
  }
}
