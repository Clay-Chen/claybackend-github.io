package com.clay.uesrcenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 登录名称
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别

     */
    private Integer gender;

    /**
     * 密码
     */
    private String userpassword;
    /**
     * 标签列表 json
     */
    private String tags;
    /**
     * 电话
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 角色 0- 普通用户
     */
    private Integer userStatus;
    /**
     * 状态
     */

    private Integer userRole;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
    /**
     * 星球编号
     */
    private String planetCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}