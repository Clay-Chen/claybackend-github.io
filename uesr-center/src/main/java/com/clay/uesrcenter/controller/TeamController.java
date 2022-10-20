package com.clay.uesrcenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clay.uesrcenter.common.BaseResponse;
import com.clay.uesrcenter.common.DeleteRequest;
import com.clay.uesrcenter.common.ErrorCode;
import com.clay.uesrcenter.common.ResultUtils;
import com.clay.uesrcenter.exception.BusinessException;
import com.clay.uesrcenter.model.domain.Team;
import com.clay.uesrcenter.model.domain.User;
import com.clay.uesrcenter.model.domain.UserTeam;
import com.clay.uesrcenter.model.dto.TeamQuery;
import com.clay.uesrcenter.model.request.*;
import com.clay.uesrcenter.model.vo.TeamUserVO;
import com.clay.uesrcenter.service.TeamService;
import com.clay.uesrcenter.service.UserService;
import com.clay.uesrcenter.service.UserTeamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.clay.uesrcenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 队伍接口
 *
 * @author clay
 */
@RestController
@RequestMapping("/team")
@CrossOrigin(originPatterns = "*",allowCredentials = "true")
@Slf4j
public class TeamController {
    @Resource
    private UserService service;
   @Resource
    private TeamService teamService;
   @Resource
   private UserTeamService userTeamService;
   @PostMapping("/add")
    public BaseResponse<Long> addTeam (@RequestBody TeamAddRequest teamAddRequest, HttpServletRequest request){
       if (teamAddRequest == null){
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
       }
       User loginUser = service.getLoginUser(request);
       Team team = new Team();
       BeanUtils.copyProperties(teamAddRequest,team);
       long teamId = teamService.addTeam(team,loginUser);

       return  ResultUtils.success(teamId);
   }



    @PostMapping("/update")
    public BaseResponse<Boolean> updateTeam (@RequestBody TeamUpdateRequest teamUpdateRequest,HttpServletRequest request){
        if (teamUpdateRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = service.getLoginUser(request);
        boolean result = teamService.updateTeam(teamUpdateRequest,loginUser);
        if (!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }
        return  ResultUtils.success(true);
    }
    @GetMapping("/get")
    public BaseResponse<Team> getTeamById(long id){
       if (id <=0){
           throw new BusinessException(ErrorCode.PARAMS_ERROR);

       }
        Team team = teamService.getById(id);
       if (team == null){
           throw  new BusinessException(ErrorCode.NULL_ERROR);
       }
       return  ResultUtils.success(team);
    }
    @GetMapping("/list")
    public BaseResponse<List<TeamUserVO>> listTeams(TeamQuery teamQuery, HttpServletRequest request) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean isAdmin = service.isAdmin(request);
        //1.查询队伍列表
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery, isAdmin);
        final List<Long> teamIdList = teamList.stream().map(TeamUserVO::getId).collect(Collectors.toList());
        //2.判断当前用户是否已加入队伍
        QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
        try {
            User loginUser = service.getLoginUser(request);
            userTeamQueryWrapper.eq("userId", loginUser.getId());
            userTeamQueryWrapper.in("teamId", teamIdList);
            List<UserTeam> userTeamList = userTeamService.list(userTeamQueryWrapper);
            //已加入的队伍id集合
            Set<Long> hasJoinTeamIdSet = userTeamList.stream().map(UserTeam::getTeamId).collect(Collectors.toSet());
            teamList.forEach(team -> {
                boolean hasJoin = hasJoinTeamIdSet.contains(team.getId());
                team.setHasJoin(hasJoin);
            });
        } catch (Exception e) {
        }
        //3.查询加入队伍的用户信息(人数)
        QueryWrapper<UserTeam> userTeamJoinQueryWrapper = new QueryWrapper<>();
        userTeamJoinQueryWrapper.in("teamId",teamIdList);
        List<UserTeam> userTeamList = userTeamService.list(userTeamJoinQueryWrapper);
        //队伍id => 加入这个队伍的用户列表
        Map<Long, List<UserTeam>> teamIdUserTeamList = userTeamList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
        teamList.forEach(team -> {
            team.setHasJoinNum(teamIdUserTeamList.getOrDefault(team.getId(),new ArrayList<>()).size());
                }
        );
        return ResultUtils.success(teamList);
    }
    @GetMapping("/list/page")
    public BaseResponse<Page<Team>> listTeamsByPage(TeamQuery teamQuery){
       if (teamQuery == null){
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
       }
        Team team = new Team();
        BeanUtils.copyProperties(teamQuery,team);
        Page<Team> page = new Page<>(teamQuery.getPageNum(), teamQuery.getPageSize());
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        Page<Team> resultPage = teamService.page(page,queryWrapper);
        return ResultUtils.success(resultPage);
    }
    @PostMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest,HttpServletRequest request){
       if (teamJoinRequest == null){
           throw new BusinessException(ErrorCode.PARAMS_ERROR);

       }
        User loginUser = service.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest,loginUser);
       return ResultUtils.success(result);

    }

    @PostMapping("/quit")
    public BaseResponse<Boolean> quitTeam(@RequestBody TeamQuitRequest teamQuitRequest, HttpServletRequest request){
       if (teamQuitRequest == null){
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
       }
        User loginUser = service.getLoginUser(request);
       boolean result = teamService.quitTeam(teamQuitRequest,loginUser);
       return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeam (@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        User loginUser = service.getLoginUser(request);
        boolean result = teamService.deleteTeam(id,loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

    /**
     * 获取我创建的队伍
     * @param teamQuery
     * @param request
     * @return
     */
    @GetMapping("/list/my/create")
    public BaseResponse<List<TeamUserVO>> listMyCreateTeams(TeamQuery teamQuery, HttpServletRequest request){
        if (teamQuery == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = service.getLoginUser(request);
        teamQuery.setUserId(loginUser.getId());
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery,true);
        return ResultUtils.success(teamList);
    }

    /**
     * 获取我加入的队伍
     * @param teamQuery
     * @param request
     * @return
     */
    @GetMapping("/list/my/join")
    public BaseResponse<List<TeamUserVO>> listMyJoinTeams(TeamQuery teamQuery, HttpServletRequest request){
        if (teamQuery == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = service.getLoginUser(request);
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",loginUser.getId());
        List<UserTeam> userTeamList = userTeamService.list(queryWrapper);
        //取出不重要的队伍 id
        //teamId userId
        //1,2
        //1,3
        //2,3
        //result
        //1 => 2,3
        //2 =>3
        Map<Long, List<UserTeam>> listMap =
                userTeamList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
        List<Long> idList = new ArrayList<>(listMap.keySet());
        teamQuery.setIdList(idList);
        List<TeamUserVO> teamList = teamService.listTeams(teamQuery,true);
        return ResultUtils.success(teamList);
    }
}
