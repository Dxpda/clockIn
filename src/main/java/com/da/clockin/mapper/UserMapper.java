package com.da.clockin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.da.clockin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
public interface UserMapper extends BaseMapper<User> {

    User login(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);

    int Register(String user,String pw,int type);

    int isRegister(String user);
}
