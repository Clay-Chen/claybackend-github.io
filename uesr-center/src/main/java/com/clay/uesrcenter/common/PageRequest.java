package com.clay.uesrcenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求参数
 */
@Data
public class PageRequest implements Serializable {


    private static final long serialVersionUID = -5921220412114640552L;
    /**
     * 页面大小
     */
    protected  int pageSize;

    /**
     * 当前是第几页
     */
    protected  int pageNum;
}
