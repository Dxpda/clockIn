package com.da.clockin.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.da.clockin.common.exception.CurrencyException;
import com.da.clockin.entity.Detail;
import com.da.clockin.entity.Domain;
import com.da.clockin.entity.Vo.DetailVo;
import com.da.clockin.entity.dto.DetailDto;
import com.da.clockin.mapper.DetailMapper;
import com.da.clockin.service.DetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.da.clockin.common.util.ThreadUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 达达
 * @since 2023-03-14
 */
@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements DetailService {

    @Resource
    private DetailMapper detailMapper;

    @Override
    public List<DetailDto> getList(int page, int limit, int value,String type) {
        page = page -1;

        Long uid = ThreadUtil.getId();
        int type1 = 0;
        if (type != null){
            if (type.equals("add")){
                type1 = 1;
            }else if (type.equals("assess")){
                int power = ThreadUtil.getPower();
                if (power != 0){
                    throw new CurrencyException("当前账号不支持评价");
                }
                type1 = 2;
            }
        }

        return detailMapper.getList(page, limit,value,type1,uid);
    }

    @Override
    public void addDetail(DetailVo detailVo) {
        Long uid = ThreadUtil.getId();
        Detail detail = new Detail();
        detail.setUId(uid);
        detail.setCreateTime(new Date());
        BeanUtils.copyProperties(detailVo,detail);

        Date date = DateUtil.date();

        LambdaQueryWrapper<Detail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Detail::getUId,uid)
                .eq(Detail::getUClass,detailVo.getUClass())
                .orderByDesc(Domain::getCreateTime)
                .eq(Domain::getIsDel,0)
                .last("limit 0,1");
        List<Detail> list = this.list(lambdaQueryWrapper);
        if (list.size() != 0){
            Date createTime = list.get(0).getCreateTime();
            //当前时间往前便宜10分钟
            DateTime dateTime = DateUtil.offsetMinute(createTime, 10);
            if (date.compareTo(dateTime) < 0){
                throw new CurrencyException("请不要重复提交!");
            }
        }

        boolean save = this.save(detail);
        if (!save){
            throw new CurrencyException("提交失败,请稍后再试!");
        }
    }

    @Override
    public Detail getDetail(Long id) {
        Detail byId = this.getById(id);
        if (byId == null){
            throw new CurrencyException("当前考勤信息不存在");
        }
        return byId;
    }

    @Override
    public void updateDetail(DetailVo detail) {
        Long uid = ThreadUtil.getId();
        LambdaUpdateWrapper<Detail> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Detail::getId,detail.getId()).eq(Detail::getUId,uid).eq(Domain::getIsDel,0);
        Detail detail1 = new Detail();
        BeanUtils.copyProperties(detail,detail1);
        boolean update = this.update(detail1,lambdaUpdateWrapper);
        if (!update){
            throw new CurrencyException("修改失败");
        }
    }

    @Override
    public void deleteDetail(Long id) {
        LambdaUpdateWrapper<Detail> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Detail::getId,id).eq(Detail::getUId,ThreadUtil.getId()).set(Domain::getIsDel,1).eq(Domain::getIsDel,0);
        boolean update = this.update(lambdaUpdateWrapper);
        if (!update){
            throw new CurrencyException("删除失败");
        }
    }
}
