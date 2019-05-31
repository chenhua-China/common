package com.chenhua.common.dao;

import com.chenhua.common.entity.Entity;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 *  公共的dao
 *  @auther chp
 *  @date 2018年12月3日12:24:37
 * @param <T>
 */
public interface BaseDao<T extends Entity> {

    /**
     * 通过id获取记录
     * @param id
     * @return
     */
    public T getById(@Param("id")Long id);

    /**
     * 更新数据
     * @param entity
     */
    public Integer updateById(T entity);

    /**
     * 保存数据
     * @param entity
     */
    public void insert(T entity);

    /**
     * 删除数据
     * @param id
     * @return
     */
    public Integer deleteById(@Param("id") Long id);

    /**
     * 获取分页数据
     * @param param
     * @return
     */
    public List<T> getList(Serializable param);

}
