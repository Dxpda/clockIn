//开发时间 : 2023/2/1 16:33

package com.da.clockin.common.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Jwt {


    private final static String JWT_SIGN = "dada";

    /**
     * 生成token
     * @param user
     * @param id
     * @return
     */
    public static String produceToken(String user,Long id,int power){
        HashMap<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        //Calendar生成时间
        Calendar instance = Calendar.getInstance();
        //添加时间有多长,Date表示天,保存一天,从生成的时间开始增加24个小时,到了指定时间就过期了
        instance.add(Calendar.DATE,1);
        String token = JWT.create()
                //设置头部可以不设定，就是使用默认的
                .withHeader(map)
                //设置载荷
                //payload  //自定义用户名和id
                .withClaim("id",id)
                .withClaim("user",user)
                .withClaim("power",power)
                //指定令牌过期时间
                .withExpiresAt(instance.getTime())
                //签名,防止被修改
                .sign(Algorithm.HMAC256(JWT_SIGN));
        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @throws Exception
     */
    public static Map<String, Object> verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SIGN)).build();
        DecodedJWT decodedJwt = null;
        try {
            decodedJwt = jwtVerifier.verify(token);
            //获取负载里面对应的内容
            Long id = decodedJwt.getClaim("id").asLong();
            String user = decodedJwt.getClaim("user").asString();
            int power = decodedJwt.getClaim("power").asInt();
            //获取过期时间
            Date time = decodedJwt.getExpiresAt();
            String s = DateUtil.formatDateTime(time);
            Map<String, Object> map = new HashMap<>();
            map.put("id",id);
            map.put("user",user);
            map.put("time",s);
            map.put("power",power);
            return map;
        }catch (JWTVerificationException e) {
            //e.printStackTrace();
            System.out.println("未知异常");
        }
        return null;
    }

    /**
     * 验证token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(JWT_SIGN)).build().verify(token);
    }


}
