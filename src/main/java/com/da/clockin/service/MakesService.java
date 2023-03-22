package com.da.clockin.service;

import com.da.clockin.entity.Makes;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
public interface MakesService extends IService<Makes> {

    void addAddess(Long id, String mark, Integer score);
}
