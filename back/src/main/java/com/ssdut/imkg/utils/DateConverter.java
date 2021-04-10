package com.ssdut.imkg.utils;


import org.springframework.core.convert.converter.Converter;

import javax.crypto.spec.PSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换
 * @Author Fan Yuanxin
 * @Date 2021/3/24 17:03
 * @Version 1.0
 */
public class DateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        try{
            return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
