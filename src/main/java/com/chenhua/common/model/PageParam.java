package com.chenhua.common.model;

import java.io.Serializable;

/**
 * 分页参数
 */
public class PageParam implements Serializable {

  private int pageNum;

  private int pageSize;

  public PageParam() {
    this.pageNum = 1;
    this.pageSize = 20;
  }

  public PageParam(int pageNum, int pageSize) {
    this.pageNum = pageNum;
    this.pageSize = pageSize;
  }

  public int getPageNum() {
    return pageNum;
  }

  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
