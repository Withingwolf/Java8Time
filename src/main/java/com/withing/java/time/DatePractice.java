package com.withing.java.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huangweixin7
 * @date 2020/2/12 21:09:04
 * description: DatePractice
 */
public class DatePractice {

    @Test
    public void currentTime() {
        Date current = new Date();
        System.out.println(current);
        current = Calendar.getInstance().getTime();
        System.out.println(current);
    }

    @Test
    public void getMillisecond() {
        Date currentDate = Calendar.getInstance().getTime();
        long millisecond = currentDate.getTime();
        System.out.println("date: " + currentDate.toString() + "millisecond: " + millisecond);
    }

    @Test
    public void beforeAndAfter() {
        Date beforeDate = new Date(2020, Calendar.JANUARY, 1);
        Date afterDate = new Date(2019, Calendar.JULY, 1);
        System.out.println("before ? " + beforeDate.before(afterDate));
        System.out.println("after ?" + afterDate.after(beforeDate));
    }

    @Test
    public String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

}
