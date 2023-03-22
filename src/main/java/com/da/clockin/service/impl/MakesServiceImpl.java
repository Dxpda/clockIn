package com.da.clockin.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.da.clockin.common.exception.CurrencyException;
import com.da.clockin.entity.Detail;
import com.da.clockin.entity.Domain;
import com.da.clockin.entity.Makes;
import com.da.clockin.mapper.MakesMapper;
import com.da.clockin.service.DetailService;
import com.da.clockin.service.MakesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.da.clockin.common.util.ThreadUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
@Service
public class MakesServiceImpl extends ServiceImpl<MakesMapper, Makes> implements MakesService {
    @Resource
    private DetailService detailService;


    @Override
    public void addAddess(Long id, String mark, Integer score) {
        Long uid = ThreadUtil.getId();
        Detail detail = detailService.getDetail(id);

        if (detail.getUId().equals(uid)){
            throw new CurrencyException("不能评价自己发布的考勤~");
        }

        Makes makes = new Makes();
        makes.setDId(id);
        makes.setUId(detail.getUId());
        makes.setScore(score);
        makes.setMake(mark);
        makes.setCreateTime(new Date());
        makes.setRootId(uid);

        //获取当天的时间,不包括小时
        DateTime parse = DateUtil.parse(DateUtil.today());

        LambdaUpdateWrapper<Makes> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.ge(Domain::getCreateTime,parse).eq(Makes::getDId,detail.getId()).eq(Makes::getRootId,uid);
        int count = this.count(lambdaUpdateWrapper);
        if (count > 0){
            throw new CurrencyException("当前考勤以评价过,请不要重复评价!");
        }
        this.save(makes);
    }
}
