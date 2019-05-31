package com.chenhua.common.util.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Random;

/**
 * 用来做des加解密
 *
 * @author chenhuaping
 * @date 2018年7月17日 下午8:00:37
 */
public class DesUtil {

  public static final String CHARSET = "utf-8";

  private static final String so = "1234567890abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM";

  /**
   * 加密
   *
   * @param keys
   * @param data
   * @return
   * @throws UnsupportedEncodingException
   * @throws InvalidKeyException
   */
  public static byte[] encode(byte[] keys, byte[] data) throws Exception {
    // 正式执行加密操作
    DESKeySpec desKeySpec = new DESKeySpec(keys);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    // 密钥
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    // 偏移量
    IvParameterSpec iv = new IvParameterSpec(keys);
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
    return cipher.doFinal(data);
  }

  /**
   * 解密
   *
   * @param keys
   * @param data
   * @return
   * @throws UnsupportedEncodingException
   * @throws InvalidKeyException
   */
  public static byte[] decode(byte[] keys, byte[] data) throws Exception {
    // 正式执行加密操作
    DESKeySpec desKeySpec = new DESKeySpec(keys);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    // 密钥
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    // 偏移量
    IvParameterSpec iv = new IvParameterSpec(keys);
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
    return cipher.doFinal(data);
  }

  /**
   * 生成随机密码
   *
   * @return
   * @throws
   */
  public static byte[] genPwd() {
    Random rand = new Random();
    String str = "";
    for (int i = 0; i < 8; i++) {
      int nextInt = rand.nextInt(62);
      String ch = so.substring(nextInt, nextInt + 1);
      str += ch;
    }
    try {
      return str.getBytes("utf-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws UnsupportedEncodingException, Exception {
    String data =
        "7748942f755e9b6d9fcc9026c11ed8271c87678f37587c36ba75292a6741e5d3e07c07dbcfeb4013096ecd328d31a8e29535c66cb2aa312f9b186cf341e8f0074c226fc023795ff0cb89a22d20d1eda1e9815a4f3d3f2ca38528de2e2df70d86436299fe192bb9a170519d2e1d2a8b81fa1606f45244425f5a6896bb19102cc06a39686df1788c6d32e7980d478943e3ba6e2811b3a27db6f2c4e3cb787b6a2e7bf6a5a5f96a27ae7eb27a9c124deeb910298cabf1c76980c82a17148d9aadf12b70fa02c60de61f622e0a20521dc4ba549404c8c7c5a08b0b131a5c43fe52a5238c5b8ae311a6b6533fe2d0832d49b3386df1a24c9fd75ad91a9a7520af3fb876e2f1322b1d8d8c6d76abb79f030db8500adaa1e4e6e4743f0bb1177a583e5e328453938b6c2cafd40926b81276ee40e65c50640135e03650a0f705f1abbaa124286bb23c969a08c29de336ee9ff98bbc32b5b00c236eef73adf7a66adea33b83b811c9ee4f3be46dc427cada6f32d0b8b9faa53c1e3f24c72cdc2fd2d675b2721441cd402efc83f011fa0cd21f409c74f390b949051cd87ed01136525cfb9bb255865613dbceaadc36ccb138dd5e09484abdd9d364c5499ac4add27c6c293ee5f02d2d9af293657d5d5223c6cd7b687ee18c9cadeaf1331c99287b83a14b024c438fd0f6aaa311cfb923d7d4b95c95ce6fdca22876a137dffef3e2ba873c10d71561809ee6611afffe05c57a0629c0a6018e0ec33b7802b6b7b4bf407debcb754145e689146385";
    byte[] decodeHex = hexDecode(data);
    byte[] decode = decode("0gXTi6au".getBytes("utf-8"), decodeHex);
    System.out.println(new String(decode, "utf-8"));
  }

  /** Hex编码, byte[]->String. */
  public static String hexEncode(byte[] input) {
    return Hex.encodeHexString(input);
  }

  /** Hex解码, String->byte[]. */
  public static byte[] hexDecode(String input) {
    try {
      return Hex.decodeHex(input.toCharArray());
    } catch (DecoderException e) {
      throw new IllegalStateException("Hex Decoder exception", e);
    }
  }
}
