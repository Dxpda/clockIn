package com.da.clockin.service;

import com.da.clockin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
public interface UserService extends IService<User> {

    User login(String user, String password);

    Integer register(String user, String password ,int type);
}
