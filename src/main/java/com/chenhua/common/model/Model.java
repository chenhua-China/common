package com.chenhua.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *  * 公共的模型类
 *  * @auther chp
 *  * @date 2018年12月3日12:24:37
 */
@Setter
@Getter
@ToString
public class Model implements Serializable {

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
