package com.chenhua.common.service;

import com.chenhua.common.dao.BaseDao;
import com.chenhua.common.entity.Entity;
import com.chenhua.common.model.Model;
import com.chenhua.common.model.PageParam;
import com.chenhua.common.model.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 公共服务类
 * @param <T>
 */
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T extends Model, D extends Entity> implements BaseService<T> {


    public  abstract BaseDao<D> getDao();

    private Class<D> clazzEntity = null;
    private Class<T> clazzModel = null;

    {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type clazzModel = pType.getActualTypeArguments()[0];
            Type clazzEntity = pType.getActualTypeArguments()[1];
            if (clazzEntity instanceof Class) {
                this.clazzEntity = (Class<D>) clazzEntity;
            }
            if (clazzModel instanceof Class) {
                this.clazzModel = (Class<T>) clazzModel;
            }
        }
    }

    /**
     * 创建model实例
     * @return
     */
    private <E> E createModelInstance(Class<E> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = false)
    public void insert(T model) {
        if (model == null){
            throw new NullPointerException();
        }
        D entity = this.createModelInstance(clazzEntity);
        BeanUtils.copyProperties(model, entity);
        this.getDao().insert(entity);
    }

    /**
     * 获取订单数据
     * @param id
     * @return
     */
    public T getById(Long id) {
        if (id == null) {
            throw new NullPointerException();
        }
        Entity entity = this.getDao().getById(id);
        if (entity == null) {
            return null;
        }
        T model = this.createModelInstance(clazzModel);
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    @Transactional(readOnly = false)
    public boolean updateById(T model) {
        if (model == null){
            throw new NullPointerException();
        }
        if (model.getId() == null) {
            throw new NullPointerException("update operation ,id can not null");
        }
        D entity = this.createModelInstance(clazzEntity);
        BeanUtils.copyProperties(model, entity);
        Integer size = this.getDao().updateById(entity);
        return size > 1 ? true : false;
    }

    @Transactional(readOnly = false)
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException();
        }
        Integer size =  this.getDao().deleteById(id);
        return size > 1 ? true : false;
    }

    public PageResult<T> getPage(PageParam pageParam) {

        if (pageParam == null) {
            pageParam = new PageParam();
        }
        Page<D> page = PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        this.getDao().getList(pageParam);
        PageResult<T> pageResult = new PageResult<>();

        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setPages(page.getPages());
        pageResult.setTotal(page.getTotal());
        if (CollectionUtils.isEmpty(page.getResult())) {
            pageResult.setData(Collections.emptyList());
            return pageResult;
        }
        List<T> list = new ArrayList<>(page.getResult().size());

        page.getResult().forEach(item->{
            T t = this.createModelInstance(clazzModel);
            BeanUtils.copyProperties(item, t);
            list.add(t);
        });

        pageResult.setData(list);
        return pageResult;
    }

    /**
     * entity转model
     * @param entity
     * @return
     */
    protected T entity2Model(D entity) {
        if (entity == null) {
            return null;
        }
        T t = this.createModelInstance(clazzModel);
        BeanUtils.copyProperties(entity, t);
        return t;
    }

    /**
     * model 转 entity
     * @param model
     * @return
     */
    protected D model2Entity(T model) {
        if (model == null) {
            return null;
        }
        D d = this.createModelInstance(clazzEntity);
        BeanUtils.copyProperties(model, d);
        return d;
    }

}
