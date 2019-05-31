package com.chenhua.common.model;

import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页列表
 *
 * @author chenhuaping
 * @date 2018年4月4日 下午4:23:44
 * @param <T>
 */
@Setter
@Getter
public class PageResult<T> {

  /**
   * 总的记录数
   */
  private Long total;

  /**
   * 总页数
   */
  private Integer pages;

  /**
   *  分页大小
   *
   */
  private Integer pageSize;

  /**
   * 当前页码
   */
  private Integer pageNum;

  /**
   * 数据列表
   */
  private java.util.List<T> data;

}
