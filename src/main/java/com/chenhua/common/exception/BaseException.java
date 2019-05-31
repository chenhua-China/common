package com.chenhua.common.exception;

@SuppressWarnings("serial")
public class BaseException extends Exception {

  private String code;

  public BaseException() {
    super();
  }

  public BaseException(String message) {
    super(message);
  }

  public BaseException(String message,String code) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
