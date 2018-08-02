package com.gulou.test.member.utils;

import org.apache.commons.lang3.tuple.Pair;

/**
 * <p>格式化</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/02 11:32
 */

public class FormatUtil {

    public static String email(String email) {
        return email.toLowerCase().trim();
    }

    public static Pair<String, String> mobile(String mobileArea, String mobileNum) {
        return Pair.of(mobileArea.toLowerCase().trim(), mobileNum.toLowerCase().trim());
    }

    public static String emailMask(String email) {
        return emailMask(email,4,email.indexOf('@') - 4);
    }

    public static String mobileMask(String mobile) {
        return emailMask(mobile,4,4);
    }

    public static String emailMask(String email, Integer start, Integer count) {
        return email.substring(0, start) + "****" + email.substring(start + count, email.length());
    }

    public static String mobileMask(String mobile, Integer start, Integer count) {
        return mobile.substring(0, start) + "****" + mobile.substring(start + count, mobile.length());
    }

    public static void main(String[] args) {
        String email = "593051107@qq.com";
        System.out.println(emailMask(email, 4, email.indexOf('@') - 4));
        String mobile = "13452202456";
        System.out.println(mobileMask(mobile, 4, 4));
    }
}
