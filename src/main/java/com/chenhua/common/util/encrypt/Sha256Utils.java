package com.chenhua.common.util.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Sha256Utils {

  /**
   * 获取签名
   *
   * @param payload
   * @param secret
   * @return
   */
  public static String base64Hmac256(String payload, String secret) {
    try {
      Mac sha256Hmac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
      sha256Hmac.init(secretKey);
      return Base64.encodeBase64String(sha256Hmac.doFinal(payload.getBytes()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取签名
   *
   * @param payload
   * @param secret
   * @return
   */
  public static byte[] hmac256(byte payload[], String secret) {
    try {
      Mac sha256Hmac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
      sha256Hmac.init(secretKey);
      return sha256Hmac.doFinal(payload);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
