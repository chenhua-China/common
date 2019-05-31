package com.chenhua.common.service;

import com.chenhua.common.model.Model;
import com.chenhua.common.model.PageParam;
import com.chenhua.common.model.PageResult;

/**
 * 公共的服务类
 * @auther chp
 * @date 2018年12月3日12:24:37
 * @param <T>
 */
public interface BaseService<T extends Model> {

    /**
     * 获取记录
     * @param id
     * @return
     */
    public T getById(Long id);

    /**
     * 保存数据
     * @param model
     */
    public void insert(T model);

    /**
     * 更新数据
     * @param model
     * @return
     */
    public boolean updateById(T model);

    /**
     * 删除数据
     * @param id
     * @return
     */
    public boolean deleteById(Long id);

    /**
     * 获取分页数据
     * @param pageParam
     * @return
     */
    public PageResult<T> getPage(PageParam pageParam);

}
