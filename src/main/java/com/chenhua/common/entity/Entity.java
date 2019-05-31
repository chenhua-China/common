package com.chenhua.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应表实体公共类
 */
@Setter
@Getter
@ToString
public class Entity implements Serializable {

    /**
     * 表对应的id
     */
    private Long id;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录更新时间
     */
    private Date updateTime;

}
