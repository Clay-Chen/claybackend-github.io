package com.clay.uesrcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clay.uesrcenter.service.UserTeamService;
import com.clay.uesrcenter.model.domain.UserTeam;
import com.clay.uesrcenter.mapper.UserTeamMapper ;
import org.springframework.stereotype.Service;

/**
* @author 12788
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2022-09-20 23:58:54
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {

}




