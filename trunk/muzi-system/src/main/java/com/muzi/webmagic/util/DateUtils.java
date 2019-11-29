package com.muzi.webmagic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化工具类
 */
public class DateUtils {

    private static SimpleDateFormat simpleDateFormat = null;

    /**
     * 获取当前时间 格式：yyyymmdd   20191128
     */
    public static String getDateFormat(){
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        return format;
    }

}
