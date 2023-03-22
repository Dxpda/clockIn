package com.da.clockin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.da.clockin.common.exception.CurrencyException;
import com.da.clockin.entity.Domain;
import com.da.clockin.entity.User;
import com.da.clockin.mapper.UserMapper;
import com.da.clockin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.da.clockin.common.util.ShiroMD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String user, String password) {
        String encryption = ShiroMD5.encryption(user, password);

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUName,user).eq(User::getPassword,encryption).eq(Domain::getIsDel,0);
        User dada = userMapper.login(lambdaQueryWrapper);
        if (dada == null){
            throw new CurrencyException("账号或密码错误!");
        }
        return dada;
    }

    @Override
    public Integer register(String user, String password, int type) {
        //判断账号是否存在
        Integer register = this.isRegister(user);
        if (register == 1){
            throw new CurrencyException("当前账号已存在!`(*>﹏<*)′");
        }
        if (register != 0){
            throw new CurrencyException("异常操作`(*>﹏<*)′");
        }

        String encryption = ShiroMD5.encryption(user, password);
        int i = userMapper.Register(user, encryption, type);
        if (i != 1){
            throw new CurrencyException("注册失败`(*>﹏<*)′");
        }
        return i;
    }

    public Integer isRegister(String user){
        return userMapper.isRegister(user);
    }
}
