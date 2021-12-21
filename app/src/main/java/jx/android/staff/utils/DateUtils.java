package jx.android.staff.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by huanghe on 16/7/30.
 */
public class DateUtils {

    public static long[][] getLastFourWeekTimeZone() {
        String format = "yyyyMMdd";
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);

        String currentSunday = getCurrentSundayInMonth(format);
        long[][] timeZones = new long[4][7];
        try {
            Date currentSundayDate = dateFormater.parse(currentSunday);
            Calendar cal = Calendar.getInstance();
            long[] tempTimeZones = new long[28];
            for (int i = 27; i >= 0; i--) {
                cal.setTime(currentSundayDate);
                cal.add(Calendar.DAY_OF_YEAR, -i);
                Date date = cal.getTime();
                tempTimeZones[27 - i] = date.getTime();
            }

            for (int i = 0; i < 28; i++) {
                timeZones[i / 7][i % 7] = tempTimeZones[i];
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return timeZones;
    }

    public static String getCurrentMondayInMonth(String format) {
        return getMondayInMonth(0, format);
    }

    public static String getCurrentSundayInMonth(String format) {
        return getSundayInMonth(0, format);
    }

    /**
     * 获取一周开始时间（字符串）
     */
    public static String getMondayInMonth(int lastOffsite, String format) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 1);

        Date date = cal.getTime();
        long lTimeOffsite = (long) lastOffsite * 7 * 24 * 60 * 60 * 1000;
        long lTime = date.getTime() - lTimeOffsite;

        date.setTime(lTime);
        return dateFormater.format(date);
    }

    /**
     * 获取一周结束时间（字符串）
     */
    public static String getSundayInMonth(int lastOffsite, String format) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));

        Date date = cal.getTime();
        long lTimeOffsite = (long) lastOffsite * 7 * 24 * 60 * 60 * 1000;
        long lTime = date.getTime() - lTimeOffsite;
        date.setTime(lTime);
        return dateFormater.format(date);
    }

    public static String getMonthDay(long time) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("MM月dd日");
        return dateFormater.format(time);
    }

    public static String getHoMiStr(long time) {
        int[] temp = getHoMiSec(time);
        String timeStr = "";
        if (temp[0] > 0) {
            timeStr += temp[0] + "时";
        }

        if (temp[1] > 0 || temp[0] > 0) {
            timeStr += temp[1] + "分";
        }

        return timeStr;
    }

    public static String getHoMiSecStr(long time) {
        int[] temp = getHoMiSec(time);
        String timeStr = "";
        if (temp[0] > 0) {
            timeStr += temp[0] + "时";
        }

        if (temp[1] > 0 || temp[0] > 0) {
            timeStr += temp[1] + "分";
        }

        timeStr += temp[2] + "秒";

        return timeStr;
    }

    public static int[] getHoMiSec(long time) {
        int sec = (int) (time / 1000);
        int mini = sec / 60;
        sec = sec % 60;
        int houre = mini / 60;
        mini = mini % 60;
        houre = houre % 24;

        return new int[]{houre, mini, sec};
    }

    public static String getDayOfWeekChinese(long time) {
        int day = getDayOfWeek(time);
        switch (day) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
        }

        return "";
    }

    public static int getDayOfWeek(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static long getNowTimeZone() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return date.getTime();
    }

    public static String getNowTime(String format) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        return dateFormater.format(date);
    }


    public static String long2StrTime(long time, String format, String timeArea) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT+" + timeArea));
        Date date = new Date(time);
        return dateFormater.format(date);
    }

    public static long strTime2Long(String timeStr, String format) throws ParseException {
        Date date = strTime2Date(timeStr, format); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = date2Long(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static Date strTime2Date(String strTime, String formatType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static long getTodayTimeZone() {
        String format = "yyyyMMdd";
        String todayStr = getNowTime(format);

        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        Date date;
        long timeZone = 0;
        try {
            date = dateFormater.parse(todayStr);
            timeZone = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeZone;
    }

    public static String date2Str(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static long date2Long(Date date) {
        return date.getTime();
    }

    public static String getHowLongAgo(long timeZone) {
        long nowTimeZone = System.currentTimeMillis();
        long subTime = nowTimeZone - timeZone;
        subTime = subTime / 1000;
        if (subTime < 60) {
            return String.valueOf(subTime) + "秒前";
        } else if (subTime < 60 * 60) {
            return String.valueOf(subTime / 60) + "分钟前";
        } else if (subTime < 60 * 60 * 24) {
            return String.valueOf(subTime / (60 * 60)) + "小时前";
        } else if (subTime < 60 * 60 * 24 * 30) {
            return String.valueOf(subTime / (60 * 60 * 24)) + "天前";
        } else if (subTime < 60 * 60 * 24 * 30 * 12) {
            return String.valueOf(subTime / (60 * 60 * 24 * 30)) + "月前";
        } else {
            return String.valueOf(subTime / (60 * 60 * 24 * 30 * 12)) + "年前";
        }
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate1(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDotDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /*
     * 将时间戳转换为时间
     * yyyy年MM月dd日 HH:mm:ss
     *
     */
    public static String stampToDateAll(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间 时：分
     */
    public static String stampToNowTime(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 获取传入月份
     */
    public static String stampTomonth(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 获取系统时间
     *
     * @return
     */
    public static long getNowDayTime() {

        long time = System.currentTimeMillis();//获取系统时间的10位的时间戳

//        String str = String.valueOf(time);

        return time;

    }

    /**
     * 计算两个时间天数差
     *
     * @param to
     * @param from
     * @return
     */
    public static int getDay(long to, long from) {
        String dateTo = stampToDate(String.valueOf(to));
        String dateFrom = stampToDate(String.valueOf(from));
        int day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy年MM月dd日");
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(dateTo);
            endDate = format.parse(dateFrom);
            day = (int) (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 当前天数到指定时间剩余天数
     *
     * @param to
     * @param
     * @return
     */
    public static String getDay(String to) {

        String dateTo = stampToDate1(String.valueOf(getNowDayTime()));
        String dateFrom = to;
        int day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate;
        java.util.Date endDate;
        try {
            beginDate = format.parse(dateTo);
            endDate = format.parse(dateFrom);
            day = ((int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000))) + 1;
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return String.valueOf(day);
    }

    /* //日期转换为时间戳 */
    public static long timeToStamp(String timers) {
        Date d = new Date();
        long timeStemp = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeStemp = d.getTime();
        return timeStemp;
    }

    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }

    public static String getNowDotTime() {
        Calendar calendar = Calendar.getInstance(); // gets current instance of the calendar
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

}
