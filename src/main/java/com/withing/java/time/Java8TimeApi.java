package com.withing.java.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Locale;

/**
 * @author huangweixin7
 * @date 2020/2/13 10:52:52
 * description: Java8TimeAPI
 */

public class Java8TimeApi {

    @Test
    public void localDate() {
        LocalDate localDate = LocalDate.of(2020, 2, 28);//年月日
        int year = localDate.getYear();//年份2020
        Month month = localDate.getMonth();//月份February
        int day = localDate.getDayOfMonth();//28
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();//FriDay
        int monthLength = localDate.lengthOfMonth();//当月天数
        boolean isLeap = localDate.isLeapYear();//是否为闰年

        //通过日期域来获取
        int fieldYear = localDate.get(ChronoField.YEAR);
        int fieldMonth = localDate.get(ChronoField.MONTH_OF_YEAR);
        int fieldDay = localDate.get(ChronoField.DAY_OF_MONTH);

    }

    @Test
    public void localTime() {
        LocalTime localTime = LocalTime.of(18, 30, 33);
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();

        int fieldHour = localTime.get(ChronoField.HOUR_OF_DAY);
    }

    @Test
    public void localDateTime() {
        LocalDate localDate = LocalDate.of(2020, 2, 28);//年月日
        LocalTime localTime = LocalTime.of(18, 30, 33);

        LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 28, 18, 30, 33);
        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);

        LocalDateTime fromDate = localDate.atTime(18, 30, 33);
        fromDate = localDate.atTime(localTime);
        LocalDateTime fromTime = localTime.atDate(localDate);

    }

    @Test
    public void instant() {
        Instant instant = Instant.ofEpochSecond(3, 1_000_000_000);
        Instant now = Instant.now();
        System.out.println(instant);
        System.out.println(now);
    }

    @Test
    public void duration() {
        LocalTime before = LocalTime.of(1, 0, 0);
        LocalTime after = LocalTime.of(2, 30, 0);
        System.out.println(before.isBefore(after));
        System.out.println(after.isAfter(before));
        Duration duration = Duration.between(before, after);
        Duration ofTime = Duration.ofMinutes(30);
        Duration ofField = Duration.of(30, ChronoUnit.SECONDS);
    }

    @Test
    public void period() {
        Period period = Period.between(LocalDate.of(2019, 1, 1), LocalDate.of(2020, 1, 1));
        Period ofField = Period.of(2, 1, 3);
    }

    @Test
    public void temporalAdjuster() {
        LocalDateTime date1 = LocalDateTime.of(2020, 2, 13, 20, 54, 0);
        LocalDateTime date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDateTime date3 = date1.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("date1 :" + date1);
        System.out.println("date2 :" + date2);
        System.out.println("date3 :" + date3);
    }

    @Test
    public void defineOwnTemporalAdjuster() {
        LocalDate today = LocalDate.of(2020, 2, 13);
        LocalDate nextWorkDay = today.with(new NextWorkDay());
        System.out.println(nextWorkDay);

        nextWorkDay = nextWorkDay.with(day -> {
            DayOfWeek dayOfWeek = DayOfWeek.of(day.get(ChronoField.DAY_OF_WEEK));
            int next = 1;
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                next = 3;
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                next = 2;
            }
            return day.plus(next, ChronoUnit.DAYS);
        });
        System.out.println(nextWorkDay);
    }

    @Test
    public void dateTimeFormat() {
        LocalDate localDate = LocalDate.of(2020, 2, 13);
        String s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(s1);
        String s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(s2);

        LocalDate day1 = LocalDate.parse("20200213", DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(day1);
        LocalDate day2 = LocalDate.parse("2020-02-13", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(day2);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate patternDate = LocalDate.parse("12/02/2020", pattern);
        System.out.println(patternDate.format(pattern));
        System.out.println(pattern.format(patternDate));

        DateTimeFormatter locatePattern = DateTimeFormatter.ofPattern("d/MMMM yyyy", Locale.US);
        LocalDate locateDate = LocalDate.of(2020, 2, 10);
        String locateString = localDate.format(locatePattern);
        System.out.println(locateString);
        System.out.println(LocalDate.parse(locateString, locatePattern));
    }

    @Test
    public void dateFormatBuilder() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral("/")
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral("/")
                .appendText(ChronoField.YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.DAY_OF_WEEK)
                .appendLiteral(" ")
                .toFormatter(Locale.US);
        System.out.println(LocalDate.of(2020, 2, 13).format(formatter));
    }

    public static class NextWorkDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek week = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            Temporal nextWorkDay = null;
            if (week.equals(DayOfWeek.FRIDAY)) {
                nextWorkDay = temporal.plus(3, ChronoUnit.DAYS);
            } else if (week.equals(DayOfWeek.SATURDAY)) {
                nextWorkDay = temporal.plus(2, ChronoUnit.DAYS);
            } else {
                nextWorkDay = temporal.plus(1, ChronoUnit.DAYS);
            }
            return nextWorkDay;
        }
    }

}
