package com.da.clockin;

import cn.hutool.core.date.DateUtil;
import com.da.clockin.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class ClockInApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {

        Date date = DateUtil.date();
        Date dateTime = DateUtil.offsetDay(date, 2);
        String timeDay2 = DateUtil.formatDateTime(dateTime);
        System.out.println(timeDay2);


    }

}
