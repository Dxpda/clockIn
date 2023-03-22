package com.da.clockin.mapper;

import com.da.clockin.entity.Detail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.da.clockin.entity.dto.DetailDto;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
public interface DetailMapper extends BaseMapper<Detail> {

    List<DetailDto> getList(int page,int limit,int value,int type,Long uid);


}
