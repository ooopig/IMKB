package com.ssdut.imkg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author Fan Yuanxin
 * @Date 2021/4/6 20:12
 * @Version 1.0
 */
public class TimeUtils {
    public static final String DEF_FMT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DEF_FMT_DATE = "yyyy-MM-dd";

    //================================== 获取现在时间 =========================================

    /**
     * 获取现在时间, 时间戳
     */
    public static long nowLog() {
        return System.currentTimeMillis();
    }

    /**
     * 获取现在时间, 日期时间
     */
    public static Date nowDate() {
        return new Date();
    }

    /**
     * 获取现在时间, 标准格式
     */
    public static String nowStr() {
        return format(nowLog(), DEF_FMT_DATE_TIME);
    }

    /**
     * 获取现在时间, 指定格式.
     */
    public static String nowStr(String pattern) {
        return format(nowLog(), pattern);
    }

    //================================== Date/long 转换 String =========================================

    /**
     * long --> String, 标准格式.
     */
    public static String format(long time) {
        return format(time, DEF_FMT_DATE_TIME);
    }

    /**
     * Date --> String, 标准格式.
     */
    public static String format(Date date) {
        return format(date, DEF_FMT_DATE_TIME);
    }

    /**
     * long --> String, 指定格式.
     */
    public static String format(long time, String pattern) {
        noNullEmpty(pattern, "样式");
        try {
            return new SimpleDateFormat(pattern).format(time);
        } catch (Exception e) {
            throw new RuntimeException("不支持的样式", e);
        }
    }

    /**
     * Date --> String, 指定格式.
     */
    public static String format(Date date, String pattern) {
        noNullEmpty(date, "时间");
        noNullEmpty(pattern, "样式");
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            throw new RuntimeException("不支持的样式", e);
        }
    }

    //================================== String 转换 Date/Long =========================================

    /**
     * 标准格式 String --> long
     */
    public static long strToLong(String dateTime) {
        return strToLong(dateTime, DEF_FMT_DATE_TIME);
    }

    /**
     * 标准格式 String --> Date
     */
    public static Date strToDate(String dateTime) {
        return strToDate(dateTime, DEF_FMT_DATE_TIME);
    }

    /**
     * 指定格式 String --> long
     */
    public static long strToLong(String dateTime, String pattern) {
        Date date = strToDate(dateTime, pattern);
        if (date == null) {
            return -1L;
        }
        return date.getTime();
    }

    /**
     * 指定格式 String --> Date
     */
    public static Date strToDate(String dateTime, String pattern) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        try {
            return new SimpleDateFormat(pattern).parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException("不支持的样式", e);
        }
    }

    //================================== 判断是否同一间段 =========================================

    /**
     * 判断两个时间是否在同一天
     */
    public static boolean isSameDay(Date time1, Date time2) {
        noNullEmpty(time1, "时间1");
        noNullEmpty(time2, "时间2");
        String tStr1 = format(time1, DEF_FMT_DATE);
        String tStr2 = format(time2, DEF_FMT_DATE);
        return tStr1.equals(tStr2);
    }

    /**
     * 判断两个时间是否在同一天
     */
    public static boolean isSameDay(long time1, long time2) {
        String tStr1 = format(time1, DEF_FMT_DATE);
        String tStr2 = format(time2, DEF_FMT_DATE);
        return tStr1.equals(tStr2);
    }

    /**
     * 判断两个时间是否在同一月
     */
    public static boolean isSameMonth(Date time1, Date time2) {
        noNullEmpty(time1, "时间1");
        noNullEmpty(time2, "时间2");
        String tStr1 = format(time1, "yyyy-MM");
        String tStr2 = format(time2, "yyyy-MM");
        return tStr1.equals(tStr2);
    }

    /**
     * 判断两个时间是否在同一月
     */
    public static boolean isSameMonth(long time1, long time2) {
        String tStr1 = format(time1, "yyyy-MM");
        String tStr2 = format(time2, "yyyy-MM");
        return tStr1.equals(tStr2);
    }

    /**
     * 判断两个时间是否在同一年
     */
    public static boolean isSameYear(Date time1, Date time2) {
        noNullEmpty(time1, "时间1");
        noNullEmpty(time2, "时间2");
        String tStr1 = format(time1, "yyyy");
        String tStr2 = format(time2, "yyyy");
        return tStr1.equals(tStr2);
    }

    /**
     * 判断两个时间是否在同一年
     */
    public static boolean isSameYear(long time1, long time2) {
        String tStr1 = format(time1, "yyyy");
        String tStr2 = format(time2, "yyyy");
        return tStr1.equals(tStr2);
    }

    //================================== 时间加减运算 =========================================

    /**
     * 基础时间增加秒
     */
    public static long addSecond(long time, int amount) {
        return add(time, Calendar.SECOND, amount);
    }

    /**
     * 基础时间增加秒
     */
    public static Date addSecond(Date date, int amount) {
        noNullEmpty(date, "时间");
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 基础时间增加秒
     */
    public static String addSecond(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(add(time, Calendar.SECOND, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加秒
     */
    public static String addSecond(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(add(time, Calendar.SECOND, amount), pattern);
    }

    /**
     * 基础时间增加分钟
     */
    public static long addMinute(long time, int amount) {
        return add(time, Calendar.MINUTE, amount);
    }

    /**
     * 基础时间增加分钟
     */
    public static Date addMinute(Date date, int amount) {
        noNullEmpty(date, "时间");
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 基础时间增加分钟
     */
    public static String addMinute(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(add(time, Calendar.MINUTE, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加分钟
     */
    public static String addMinute(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(add(time, Calendar.MINUTE, amount), pattern);
    }

    /**
     * 基础时间增加小时
     */
    public static long addHour(long time, int amount) {
        return add(time, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 基础时间增加小时
     */
    public static Date addHour(Date date, int amount) {
        noNullEmpty(date, "时间");
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 基础时间增加小时
     */
    public static String addHour(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(add(time, Calendar.HOUR_OF_DAY, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加小时
     */
    public static String addHour(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(add(time, Calendar.HOUR_OF_DAY, amount), pattern);
    }

    /**
     * 基础时间增加天数
     */
    public static long addDay(long time, int amount) {
        return add(time, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 基础时间增加天数
     */
    public static Date addDay(Date date, int amount) {
        noNullEmpty(date, "时间");
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 基础时间增加天数
     */
    public static String addDay(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(add(time, Calendar.DAY_OF_MONTH, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加天数
     */
    public static String addDay(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(add(time, Calendar.DAY_OF_MONTH, amount), pattern);
    }

    /**
     * 基础时间增加月数
     */
    public static long addMonth(long time, int amount) {
        return add(time, Calendar.MONTH, amount);
    }

    /**
     * 基础时间增加月数
     */
    public static Date addMonth(Date date, int amount) {
        noNullEmpty(date, "时间");
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 基础时间增加月数
     */
    public static String addMonth(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(add(time, Calendar.MONTH, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加月数
     */
    public static String addMonth(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(add(time, Calendar.MONTH, amount), pattern);
    }

    /**
     * 基础时间增加年数
     */
    public static long addYear(long time, int amount) {
        return add(time, Calendar.YEAR, amount);
    }

    /**
     * 基础时间增加年数
     */
    public static Date addYear(Date date, int amount) {
        noNullEmpty(date, "时间");
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 基础时间增加年数
     */
    public static String addYear(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(add(time, Calendar.YEAR, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加年数
     */
    public static String addYear(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(add(time, Calendar.YEAR, amount), pattern);
    }

    /**
     * 基础时间增加秒
     */
    public static long subSecond(long time, int amount) {
        return sub(time, Calendar.SECOND, amount);
    }

    /**
     * 基础时间增加秒
     */
    public static Date subSecond(Date date, int amount) {
        noNullEmpty(date, "时间");
        return sub(date, Calendar.SECOND, amount);
    }

    /**
     * 基础时间增加秒
     */
    public static String subSecond(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(sub(time, Calendar.SECOND, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加秒
     */
    public static String subSecond(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(sub(time, Calendar.SECOND, amount), pattern);
    }

    /**
     * 基础时间增加分钟
     */
    public static long subMinute(long time, int amount) {
        return sub(time, Calendar.MINUTE, amount);
    }

    /**
     * 基础时间增加分钟
     */
    public static Date subMinute(Date date, int amount) {
        noNullEmpty(date, "时间");
        return sub(date, Calendar.MINUTE, amount);
    }

    /**
     * 基础时间增加分钟
     */
    public static String subMinute(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(sub(time, Calendar.MINUTE, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加分钟
     */
    public static String subMinute(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(sub(time, Calendar.MINUTE, amount), pattern);
    }

    /**
     * 基础时间增加小时
     */
    public static long subHour(long time, int amount) {
        return sub(time, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 基础时间增加小时
     */
    public static Date subHour(Date date, int amount) {
        noNullEmpty(date, "时间");
        return sub(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 基础时间增加小时
     */
    public static String subHour(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(sub(time, Calendar.HOUR_OF_DAY, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加小时
     */
    public static String subHour(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(sub(time, Calendar.HOUR_OF_DAY, amount), pattern);
    }

    /**
     * 基础时间增加天数
     */
    public static long subDay(long time, int amount) {
        return sub(time, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 基础时间增加天数
     */
    public static Date subDay(Date date, int amount) {
        noNullEmpty(date, "时间");
        return sub(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 基础时间增加天数
     */
    public static String subDay(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(sub(time, Calendar.DAY_OF_MONTH, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加天数
     */
    public static String subDay(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(sub(time, Calendar.DAY_OF_MONTH, amount), pattern);
    }

    /**
     * 基础时间增加月数
     */
    public static long subMonth(long time, int amount) {
        return sub(time, Calendar.MONTH, amount);
    }

    /**
     * 基础时间增加月数
     */
    public static Date subMonth(Date date, int amount) {
        noNullEmpty(date, "时间");
        return sub(date, Calendar.MONTH, amount);
    }

    /**
     * 基础时间增加月数
     */
    public static String subMonth(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(sub(time, Calendar.MONTH, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加月数
     */
    public static String subMonth(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(sub(time, Calendar.MONTH, amount), pattern);
    }

    /**
     * 基础时间增加年数
     */
    public static long subYear(long time, int amount) {
        return sub(time, Calendar.YEAR, amount);
    }

    /**
     * 基础时间增加年数
     */
    public static Date subYear(Date date, int amount) {
        noNullEmpty(date, "时间");
        return sub(date, Calendar.YEAR, amount);
    }

    /**
     * 基础时间增加年数
     */
    public static String subYear(String dateTime, int amount) {
        noNullEmpty(dateTime, "时间");
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return format(sub(time, Calendar.YEAR, amount), DEF_FMT_DATE_TIME);
    }

    /**
     * 基础时间增加年数
     */
    public static String subYear(String dateTime, String pattern, int amount) {
        noNullEmpty(dateTime, "时间");
        noNullEmpty(pattern, "样式");
        long time = strToLong(dateTime, pattern);
        return format(sub(time, Calendar.YEAR, amount), pattern);
    }

    //================================== 取时间字段(年,月,日,时,分,秒) =========================================

    /**
     * 获取指定时间的秒
     */
    public static int getSecond(long time) {
        return getField(time, Calendar.SECOND);
    }

    /**
     * 获取指定时间的分
     */
    public static int getMinute(long time) {
        return getField(time, Calendar.MINUTE);
    }

    /**
     * 获取指定时间的小时
     */
    public static int getHour(long time) {
        return getField(time, Calendar.HOUR);
    }

    /**
     * 获取指定时间的日
     */
    public static int getDay(long time) {
        return getField(time, Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取指定时间的月份
     */
    public static int getMonth(long time) {
        return getField(time, Calendar.MONTH);
    }

    /**
     * 获取指定时间的年份
     */
    public static int getYear(long time) {
        return getField(time, Calendar.YEAR);
    }

    /**
     * 获取指定时间的秒
     */
    public static int getSecond(Date date) {
        return getField(date, Calendar.SECOND);
    }

    /**
     * 获取指定时间的分
     */
    public static int getMinute(Date date) {
        return getField(date, Calendar.MINUTE);
    }

    /**
     * 获取指定时间的小时
     */
    public static int getHour(Date date) {
        return getField(date, Calendar.HOUR);
    }

    /**
     * 获取指定时间的天
     */
    public static int getDay(Date date) {
        return getField(date, Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取指定时间的月份
     */
    public static int getMonth(Date date) {
        return getField(date, Calendar.MONTH);
    }

    /**
     * 获取指定时间的年份
     */
    public static int getYear(Date date) {
        return getField(date, Calendar.YEAR);
    }

    /**
     * 获取指定时间的秒
     */
    public static int getSecond(String dateTime) {
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return getField(time, Calendar.SECOND);
    }

    /**
     * 获取指定时间的分
     */
    public static int getMinute(String dateTime) {
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return getField(time, Calendar.MINUTE);
    }

    /**
     * 获取指定时间的小时
     */
    public static int getHour(String dateTime) {
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return getField(time, Calendar.HOUR);
    }

    /**
     * 获取指定时间的日
     */
    public static int getDay(String dateTime) {
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return getField(time, Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取指定时间的月份
     */
    public static int getMonth(String dateTime) {
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return getField(time, Calendar.MONTH);
    }

    /**
     * 获取指定时间的年份
     */
    public static int getYear(String dateTime) {
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return getField(time, Calendar.YEAR);
    }

    /**
     * 获取指定时间的秒
     */
    public static int getSecond(String dateTime, String pattern) {
        long time = strToLong(dateTime, pattern);
        return getField(time, Calendar.SECOND);
    }

    /**
     * 获取指定时间的分
     */
    public static int getMinute(String dateTime, String pattern) {
        long time = strToLong(dateTime, pattern);
        return getField(time, Calendar.MINUTE);
    }

    /**
     * 获取指定时间的小时
     */
    public static int getHour(String dateTime, String pattern) {
        long time = strToLong(dateTime, pattern);
        return getField(time, Calendar.HOUR);
    }

    /**
     * 获取指定时间的日
     */
    public static int getDay(String dateTime, String pattern) {
        long time = strToLong(dateTime, pattern);
        return getField(time, Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取指定时间的月份
     */
    public static int getMonth(String dateTime, String pattern) {
        long time = strToLong(dateTime, pattern);
        return getField(time, Calendar.MONTH);
    }

    /**
     * 获取指定时间的年份
     */
    public static int getYear(String dateTime, String pattern) {
        long time = strToLong(dateTime, pattern);
        return getField(time, Calendar.YEAR);
    }

    /**
     * 获取指定时间的星期几
     */
    public static String getWeek(long time) {
        return weeks[getField(time, Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取指定时间的星期几
     */
    public static String getWeek(Date date) {
        return weeks[getField(date, Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取指定时间的星期几
     */
    public static String getWeek(String dateTime) {
        long time = strToLong(dateTime, DEF_FMT_DATE_TIME);
        return weeks[getField(time, Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取指定时间的星期几
     */
    public static String getWeek(String dateTime, String pattern) {
        long time = strToLong(dateTime, pattern);
        return weeks[getField(time, Calendar.DAY_OF_WEEK) - 1];
    }

    //================================== private =========================================

    private static String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Date add(final Date date, final int field, final int amount) {
        noNullEmpty(date, "时间");
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, amount);
        return c.getTime();
    }

    private static long add(final long time, final int field, final int amount) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.add(field, amount);
        return c.getTimeInMillis();
    }

    private static Date sub(final Date date, final int field, final int amount) {
        noNullEmpty(date, "时间");
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, -amount);
        return c.getTime();
    }

    private static long sub(final long time, final int field, final int amount) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.add(field, -amount);
        return c.getTimeInMillis();
    }

    private static int getField(final Date date, final int field) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(field);
    }

    private static int getField(final long time, final int field) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return c.get(field);
    }

    private static void noNullEmpty(Object o, String paramName) {
        if (o == null) {
            throw new RuntimeException(paramName + " 不能为空");
        }
        if (o instanceof String && ((String) o).length() < 1) {
            throw new RuntimeException(paramName + " 不能为空");
        }
    }

}
