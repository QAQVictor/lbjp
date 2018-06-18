package com.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
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
    public static final String DETAILED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String BASE_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 获取当前时间，并返回字符串
     *
     * @return yyyy-MM-dd HH:mm::ss;
     */
    public static String getNowDate() {
        DATE_FORMAT = new SimpleDateFormat(DETAILED_DATE_FORMAT);
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 将字符串按照某种格式转化为Date类
     *
     * @param dateFormat
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date formatToDate(String dateFormat, String date) throws ParseException {
        DATE_FORMAT = new SimpleDateFormat(dateFormat);
        return DATE_FORMAT.parse(date);
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

    /**
     * 根据创建时间创建id
     *
     * @param date
     * @return
     */
    public static String getIDByDate(String date) {
        return date.replace(":", "").replace("-", "").replace(" ", "") +
                NUMBER_FORMAT.format(RANDOM.nextInt(10000));
    }

    /**
     * 把时间设置为今天的23:59:59
     *
     * @param date
     * @return
     */
    public static String setEndDate(String date) {
        try {
            Date date1 = formatToDate(BASE_DATE_FORMAT, date);
            date1.setTime(date1.getTime() + 86399999);
            return DATE_FORMAT.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * 判断两个时间大小
     *
     * @param dateStr1
     * @param dateStr2
     * @return 1=date1大于date2；0=时间相同；-1=date1小于date2
     * @throws ParseException
     */
    public static int compareDate(String dateStr1, String dateStr2) {
        DATE_FORMAT = new SimpleDateFormat(DETAILED_DATE_FORMAT);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = DATE_FORMAT.parse(dateStr1);
            date2 = DATE_FORMAT.parse(dateStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
