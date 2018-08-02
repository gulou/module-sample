package com.gulou.test.common.utils;

/**
 * <p>密码工具</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/02 13:01
 */

public class PasswordUtil {

    public static String create(String passwd){
        return passwd;
    }

    public static Boolean valid(String passwd,String real){
        return passwd.equals(real) ;
    }
}
