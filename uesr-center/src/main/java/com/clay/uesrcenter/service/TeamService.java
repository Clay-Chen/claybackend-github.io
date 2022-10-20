package com.clay.uesrcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clay.uesrcenter.model.domain.Team;
import com.clay.uesrcenter.model.domain.User;
import com.clay.uesrcenter.model.dto.TeamQuery;
import com.clay.uesrcenter.model.request.TeamJoinRequest;
import com.clay.uesrcenter.model.request.TeamQuitRequest;
import com.clay.uesrcenter.model.request.TeamUpdateRequest;
import com.clay.uesrcenter.model.vo.TeamUserVO;

import java.util.List;

/**
* @author 12788
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2022-09-20 23:55:06
*/
public interface TeamService extends IService<Team> {
    /**
     * 创建队伍
     *
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);
    /**
     * 搜索队伍
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍
     * @param loginUser
     * @param teamUpdateRequest
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest,User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);
    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除(解散)队伍
     * @param id
     * @return
     */
    boolean deleteTeam(long id, User loginUser);
}
