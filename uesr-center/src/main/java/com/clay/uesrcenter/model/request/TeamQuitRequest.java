package com.clay.uesrcenter.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户退出队伍请求体
 *
 * @author yupi
 */
@Data
public class TeamQuitRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * id
     */
    private Long teamId;
}
