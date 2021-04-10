package com.ssdut.imkg.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author Fan Yuanxin
 * @Date 2021/3/15 21:25
 * @Version 1.0
 */
@Component
public class EncodePasswordUtils {

    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
