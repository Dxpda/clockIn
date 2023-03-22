//开发时间 : 2023/2/2 16:07

package com.da.clockin.common.util;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Value;

public class ShiroMD5 {


    private final static String SLAT = "dada";


    private final static Integer ITERATION = 100;



    //加密
    public static String encryption(String phone,String password){
        Md5Hash md5Hash = new Md5Hash(phone,SLAT,ITERATION);
        Md5Hash md5Hash1 = new Md5Hash(password,md5Hash.toString(),ITERATION);
        return md5Hash1.toString();
    }
}
