package com.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: 李亚卿
 * @Date: Created in 09:35 2018/4/15 0015
 * @Description:
 */
public class DateUtils {

    public static DateFormat DATE_FORMAT;
    public static DecimalFormat NUMBER_FORMAT = new DecimalFormat("0000");
    public static Random RANDOM = new Random();
    public static final String CREATE_DATE_FORMAT = "yyyy-MM-dd HH:mm::ss";

    /**
     * 获取当前时间，并返回字符串
     *
     * @return yyyy-MM-dd HH:mm::ss;
     */
    public static String getNowDate() {
        DATE_FORMAT = new SimpleDateFormat(CREATE_DATE_FORMAT);
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 生成id,格式为年月日时分秒+四位随机数
     *
     * @return String
     */
    public static String getID() {
        DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
        return DATE_FORMAT.format(new Date()) + NUMBER_FORMAT.format(RANDOM.nextInt(10000));
    }

}
