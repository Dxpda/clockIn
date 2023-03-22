//开发时间 : 2023/3/13 21:15

package com.da.clockin.controller;

import cn.hutool.core.util.RandomUtil;
import com.da.clockin.common.exception.CurrencyException;
import com.da.clockin.entity.Detail;
import com.da.clockin.entity.User;
import com.da.clockin.entity.Vo.DetailVo;
import com.da.clockin.entity.dto.DetailDto;
import com.da.clockin.service.DetailService;
import com.da.clockin.service.MakesService;
import com.da.clockin.service.UserService;
import com.da.clockin.common.util.FileTypeUtils;
import com.da.clockin.common.util.Jwt;
import com.da.clockin.common.util.Result;
import com.da.clockin.common.util.ThreadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequestMapping("/api")
@RestController
public class Test {

    @Resource
    private UserService userService;

    @Resource
    private DetailService detailService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${file.uClass}")
    private String path;

    @PostMapping("/dada")
    public Result<String> da(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        return Result.error("成功!" + name, 401);
    }

    /**
     * 登录
     * @param map
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> map, HttpServletResponse response) {
        String user = map.get("user");
        String password = map.get("password");

        if ("".equals(user) || "".equals(password)) {
            throw new CurrencyException("输入有误!");
        }
        if (user == null || password == null) {
            throw new CurrencyException("输入内容不能为空!");
        }


        User login = userService.login(user, password);

        String s = Jwt.produceToken(user, login.getId(),login.getType());
        Cookie cookie = new Cookie("token", s);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        response.addCookie(cookie);
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("token", s);
        map1.put("uid", login.getId());
        map1.put("user", login.getUName());
        map1.put("power",login.getType());
        return Result.success(map1);
    }

    /**
     * 注册
     * @param map
     * @return
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> map) {

        System.out.println(map);
        String user = map.get("user");
        String password = map.get("password");
        String sms = map.get("sms");
        if ("".equals(user) || "".equals(password) || "".equals(sms)) {
            throw new CurrencyException("输入有误!");
        }
        if (user == null || password == null || sms == null) {
            throw new CurrencyException("输入内容不能为空!");
        }
        int type = Integer.parseInt(map.get("type"));
        if (type != 0 && type != 1) {
            throw new CurrencyException("账号注册类型有误");
        }

        String path = "register:" + user + ":" + password;
        String s = stringRedisTemplate.opsForValue().get(path);
        if ("".equals(s) || s == null) {
            throw new CurrencyException("请输入验证码!");
        }
        if (!s.equals(sms)) {
            throw new CurrencyException("验证码有误!");
        }

        Integer register = userService.register(user, password, type);
        if (register != 1) {
            throw new CurrencyException("注册失败,请稍后再试!");
        }

        Boolean delete = stringRedisTemplate.delete(path);
        if (Boolean.FALSE.equals(delete)) {
            throw new CurrencyException("系统繁忙,请稍后再试!");
        }
        return Result.success("注册成功!");
    }


    @PostMapping("getsms")
    public Result<Map<String, String>> getsms(@RequestBody Map<String, String> map){
        String user = map.get("user");
        String password = map.get("password");
        String path = "register:" + user + ":" + password;
        String s = stringRedisTemplate.opsForValue().get(path);
        if (s == null){
            throw new CurrencyException("请验证账号或密码是否填写正确!");
        }
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("value",s);
        map1.put("msg","公益项目,请不要瞎搞,谢谢~");
        return Result.success(map1);

    }

    /**
     * 验证码
     * @param map
     * @return
     */
    @PostMapping("/sms")
    public Result<String> sms(@RequestBody Map<String, String> map) {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String password = map.get("password");
        String user = map.get("user");
        String path = "register:" + user + ":" + password;
        String i = RandomUtil.randomString(6);
        stringRedisTemplate.opsForValue().set(path, i, 3, TimeUnit.MINUTES);
        System.out.println(i);
        return Result.success("生成成功,三分钟内有效!");
    }

    /**
     * 考勤数据
     * @param page
     * @param limit
     * @param value
     * @return
     */
    @GetMapping("/getList")
    public Result<List<DetailDto>> getList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, String value,String type) {
        if (value == null || "".equals(value)) {
            value = "0";
        }
        int value1 = Integer.parseInt(value);

        List<DetailDto> list = detailService.getList(page, limit, value1,type);
        List<DetailDto> collect = list.stream().map(s -> {
            if (s.getType().equals("0")) {
                s.setType("管理员");
            } else if (s.getType().equals("1")) {
                s.setType("老师");
            } else {
                s.setType("异常账号");
            }
            return s;
        }).toList();

        return Result.success(collect);
    }

    /**
     * 提交
     * @param detailVo
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody @Validated DetailVo detailVo) {

        detailService.addDetail(detailVo);

        return Result.success("成功!");
    }

    //接收图片
    @PostMapping("/uploads")
    public Result<List<String>> uploads(@RequestBody MultipartFile file) {
        if (ObjectUtils.isEmpty(file) || file.getSize() <= 0) {
            throw new CurrencyException("图片不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        Boolean aTrue = FileTypeUtils.isTrue(substring);
        if (!aTrue) {
            throw new CurrencyException("图片有误");
        }

        String uuid = UUID.randomUUID().toString();
        String f = path + uuid + substring;
        File file1 = new File(path);
        if (!file1.exists()) {
            boolean mkdir = file1.mkdir();
            if (!mkdir) {
                throw new CurrencyException("图片上传失败,请稍后再试");
            }
        }
        try {
            file.transferTo(new File(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(Arrays.asList(uuid + substring));
    }

    //考勤详情
    @PostMapping("/getDetail")
    public Result<Detail> getDetail(@RequestBody Detail detail) {
        Long id = detail.getId();
        Detail detail1 = detailService.getDetail(id);

        return Result.success(detail1);
    }

    //考勤修改
    @PutMapping("/detail")
    public Result<String> updateDetail(@RequestBody @Validated DetailVo detail) {
        detailService.updateDetail(detail);
        return Result.success("修改成功!");

    }

    //考勤删除
    @DeleteMapping("/detail")
    public Result<String> deleteDetail(@RequestBody Map<String, Long> map) {
        if (map == null) {
            throw new CurrencyException("非法操作!");
        }
        Long id = map.get("id");

        detailService.deleteDetail(id);
        System.out.println(map);
        return Result.success("ok");
    }

    @Resource
    private MakesService makesService;

    //评价
    @PostMapping("/assess")
    public Result<String> addAssess(@RequestBody Map<String, String> map) {
        int power = ThreadUtil.getPower();
        if (power != 0){
            throw new CurrencyException("你没有权限评价!");
        }
        long id;
        String mark;
        int score;
        try {
            id = Long.parseLong(map.get("id"));
            mark = map.get("mark");
            score = Integer.parseInt(map.get("score"));
        } catch (Exception e) {
            throw new CurrencyException("输入的内容有误");
        }

        if (score > 100 ){
            throw new CurrencyException("成绩的范围是0~100");
        }

        makesService.addAddess(id,mark,score);
        return Result.success("评价成功!");
    }
}
