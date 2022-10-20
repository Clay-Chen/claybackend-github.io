package com.clay.uesrcenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author 12788
 */
@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = -2966747430644324413L;

    private String userAccount;

    private String userPassword;


}
