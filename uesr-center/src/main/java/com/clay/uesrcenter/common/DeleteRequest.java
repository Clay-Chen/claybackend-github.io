package com.clay.uesrcenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除请求请求参数
 */
@Data
public class DeleteRequest implements Serializable {


    private static final long serialVersionUID = -5921220412114640552L;
   private long id;
}
