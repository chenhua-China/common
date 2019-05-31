package com.chenhua.common.exception;

@SuppressWarnings("serial")
public class BaseRuntimeException extends RuntimeException {

  private String code;

  public BaseRuntimeException() {
    super();
  }

  public BaseRuntimeException(String message) {
    super(message);
  }

  public BaseRuntimeException(Throwable ex) {
    super(ex);
  }

  public BaseRuntimeException(String message,String code) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
