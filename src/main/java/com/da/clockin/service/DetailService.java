package com.da.clockin.service;

import com.da.clockin.entity.Detail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.da.clockin.entity.Vo.DetailVo;
import com.da.clockin.entity.dto.DetailDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
public interface DetailService extends IService<Detail> {

    List<DetailDto> getList(int page, int limit, int value,String type);

    void addDetail(DetailVo detailVo);

    Detail getDetail(Long id);

    void updateDetail(DetailVo detail);

    void deleteDetail(Long id);
}
