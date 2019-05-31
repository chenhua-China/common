package com.chenhua.common.util.encrypt;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * rsa加密
 *
 * @author chenhuaping
 * @date 2018年7月17日 下午8:34:19
 */
public class RsaUtils {

  private static final int KEY_LENGTH = 2048;
  private static final int RESERVE_SIZE = 11;
  private static final String CHARSET = "utf-8";

  /**
   * 解密
   *
   * @param data
   * @param privateKey
   * @return
   * @throws Exception
   */
  // RSA/ECB/PKCS1Padding
  public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
    int keyByteSize = KEY_LENGTH / 8;
    int decryptBlockSize = keyByteSize - RESERVE_SIZE;
    int nBlock = data.length / keyByteSize;
    ByteArrayOutputStream outbuf = null;
    try {
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);

      outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
      for (int offset = 0; offset < data.length; offset += keyByteSize) {
        int inputLen = data.length - offset;
        if (inputLen > keyByteSize) {
          inputLen = keyByteSize;
        }
        byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
        outbuf.write(decryptedBlock);
      }
      outbuf.flush();
      return outbuf.toByteArray();
    } catch (Exception e) {
      throw new Exception("DEENCRYPT ERROR:", e);
    } finally {
      try {
        if (outbuf != null) {
          outbuf.close();
        }
      } catch (Exception e) {
        outbuf = null;
        throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);
      }
    }
  }

  /**
   * 加密
   *
   * @param key
   * @param data
   * @return
   */
  // RSA/ECB/PKCS1Padding
  public static byte[] encode(byte[] data, PublicKey publicKey) throws Exception {
    int keyByteSize = KEY_LENGTH / 8;
    int decryptBlockSize = keyByteSize - RESERVE_SIZE;
    int nBlock = data.length / keyByteSize;
    ByteArrayOutputStream outbuf = null;
    try {
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);

      outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
      for (int offset = 0; offset < data.length; offset += keyByteSize) {
        int inputLen = data.length - offset;
        if (inputLen > keyByteSize) {
          inputLen = keyByteSize;
        }
        byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
        outbuf.write(decryptedBlock);
      }
      outbuf.flush();
      return outbuf.toByteArray();
    } catch (Exception e) {
      throw new Exception("ENCRYPT ERROR:", e);
    } finally {
      try {
        if (outbuf != null) {
          outbuf.close();
        }
      } catch (Exception e) {
        outbuf = null;
        throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);
      }
    }
  }

  /**
   * 获取 私钥key
   *
   * @param pfxPath
   * @param pfxPasswd
   * @return
   * @throws Exception
   */
  public static PrivateKey getPrivateKeyFromPfx(String pfxPath, String pfxPasswd) throws Exception {
    // 替换为自己的客户端私钥路径
    try {
      File file = new File(pfxPath);
      if (!file.exists()) {
        throw new FileNotFoundException("未找到是要文件 ：" + pfxPath);
      }
      FileInputStream fis2 = new FileInputStream(file);
      KeyStore ks = KeyStore.getInstance("PKCS12");
      char[] keypwd = pfxPasswd.toCharArray(); // 证书密码
      ks.load(fis2, keypwd);
      String alias;
      alias = ks.aliases().nextElement();
      PrivateKey prikey = (PrivateKey) ks.getKey(alias, keypwd);
      fis2.close();
      return prikey;
    } catch (FileNotFoundException e) {
      throw new Exception("找不到私钥文件");
    } catch (NoSuchAlgorithmException e) {
      throw new Exception("无此算法");
    } catch (CertificateException e) {
      throw new Exception("证书加载异常");
    } catch (KeyStoreException e) {
      throw new Exception("证书加载异常");
    } catch (UnrecoverableKeyException e) {
      throw new Exception("无此算法");
    } catch (IOException e) {
      throw new Exception("文件IO异常");
    } // 加载证书
  }

  /**
   * 获取公钥key
   *
   * @param cerPath
   * @return
   * @throws Exception
   */
  public static PublicKey getPublicKeyFromCer(String cerPath) throws Exception {
    try {
      CertificateFactory certificate_factory = CertificateFactory.getInstance("X.509");
      // 替换生产环境的金运通公钥证书
      File file = new File(cerPath);
      FileInputStream file_inputstream = new FileInputStream(file);
      X509Certificate x509certificate =
          (X509Certificate) certificate_factory.generateCertificate(file_inputstream);
      PublicKey pk = x509certificate.getPublicKey();
      return pk;
    } catch (CertificateException e) {
      throw new Exception("证书加载异常");
    } catch (FileNotFoundException e) {
      throw new Exception("找不到公钥文件");
    }
  }

  /**
   * RSA签名
   *
   * @param localPrivKey 私钥
   * @param plaintext 需要签名的信息
   * @return byte[]
   * @throws Exception
   */
  public static byte[] signRSA(byte[] plainBytes, PrivateKey privateKey) throws Exception {
    String SIGNATURE_ALGORITHM = "SHA1withRSA";
    Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
    signature.initSign(privateKey);
    signature.update(plainBytes);
    return signature.sign();
  }

  /**
   * 验签操作
   *
   * @param plainBytes 需要验签的信息
   * @param signBytes 签名信息
   * @return boolean
   */
  public static boolean verifyRSA(byte[] plainBytes, byte[] signBytes, PublicKey publicKey)
      throws Exception {
    boolean isValid = false;
    String SIGNATURE_ALGORITHM = "SHA1withRSA";
    Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
    signature.initVerify(publicKey);
    signature.update(plainBytes);
    isValid = signature.verify(signBytes);
    return isValid;
  }
}
