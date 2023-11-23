package com.liveme.demo.cal;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author xuliang
 * @version 1.0
 * @project demo
 * @description
 * @date 2023/11/17 15:37:50
 */
public class Recurrence {
    public static void main(String[] args) {
//        LocalDate startDate = LocalDate.of(2023, 1, 1); // 开始日期
//        LocalDate endDate = LocalDate.of(2023, 12, 31); // 结束日期
//        int weeklyInterval = 1; // 每周间隔
//        DayOfWeek targetDayOfWeek = DayOfWeek.MONDAY; // 目标星期几
//        int nthDayOfWeek = 2; // 第几个星期几，例如第2个星期一
//        DayOfWeek lastDayOfWeek = DayOfWeek.FRIDAY; // 最后一个星期几
//
//        List<LocalDate> dates = getDatesInRange(startDate, endDate, weeklyInterval, targetDayOfWeek, nthDayOfWeek, lastDayOfWeek);
//
//        System.out.println("Dates:");
//        for (LocalDate date : dates) {
//            System.out.println(date);
//        }


//      lastDayOfWeek(Calendar.FRIDAY);
//
//
//        LocalDate currentDate = LocalDate.now();
//        int totalWeeks = currentDate.with(TemporalAdjusters.lastDayOfMonth())
//                .get(WeekFields.ISO.weekOfMonth());
//
//        System.out.println("Total weeks in the current month: " + totalWeeks);
        int targetDayOfWeek = Calendar.THURSDAY; // 目标星期几

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        while (calendar.get(Calendar.DAY_OF_WEEK) != targetDayOfWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        System.out.println("Last week's " + getDayOfWeekName(targetDayOfWeek) + " of the current month: " + calendar.getTime());
    }

    public static List<LocalDate> getDatesInRange(LocalDate startDate, LocalDate endDate, int weeklyInterval, DayOfWeek targetDayOfWeek, int nthDayOfWeek, DayOfWeek lastDayOfWeek) {
        List<LocalDate> dates = new ArrayList<>();

        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            if (current.getDayOfWeek() == targetDayOfWeek) {
                if (current.with(TemporalAdjusters.dayOfWeekInMonth(nthDayOfWeek, targetDayOfWeek)).isEqual(current)) {
                    dates.add(current);
                }
                if (current.with(TemporalAdjusters.lastInMonth(lastDayOfWeek)).isEqual(current)) {
                    dates.add(current);
                }
            }
            current = current.plusWeeks(weeklyInterval);
        }

        return dates;
    }


    public static List<Long> repeatWeek() {
        List<Long> repeatDayInstances = new ArrayList<>();
        Integer interval = 2;
        Long endTime = 1702800899000l;
        /**获取预约日程开始时间,格式化：HH:MM*/
        Calendar schedulerCalendar = Calendar.getInstance();
        schedulerCalendar.setTimeInMillis(1672560899000l);
        /**获取今天是周几*/
        int curWeekDayNum = schedulerCalendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("curDayOfWeek:"+curWeekDayNum);
        List<Integer> targetDaysOfWeek = new ArrayList<>();
        targetDaysOfWeek.add(Calendar.MONDAY);
        targetDaysOfWeek.add(Calendar.FRIDAY);

        int generateCnt = 0;
        boolean isBreak = false;
        for (int i = 0; i < 365; i++) {
            if (generateCnt > 365) {
                break;
            }
            for (int dayOfWeek : targetDaysOfWeek) {
                if (dayOfWeek < curWeekDayNum) {
                    continue;
                }
                schedulerCalendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                if (schedulerCalendar.getTimeInMillis() > endTime) {
                    isBreak = true;
                    break;
                }
                repeatDayInstances.add(schedulerCalendar.getTimeInMillis());
                generateCnt++;
            }
            if (isBreak) {
                break;
            }
            schedulerCalendar.add(Calendar.WEEK_OF_MONTH, interval);
        }
        return repeatDayInstances;
    }


    public static void lastDayOfWeek(int targetDayOfWeek){
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        while (calendar.get(Calendar.MONTH) == currentMonth) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == targetDayOfWeek) {
                int currentWeek = calendar.get(Calendar.WEEK_OF_MONTH);
                calendar.add(Calendar.DAY_OF_MONTH, 7);

                if (calendar.get(Calendar.MONTH) != currentMonth || calendar.get(Calendar.WEEK_OF_MONTH) != currentWeek) {
                    calendar.add(Calendar.DAY_OF_MONTH, -7);
                    break;
                }
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        int lastWeekDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("Last week's " + getDayOfWeekName(targetDayOfWeek) + " of the current month: " + lastWeekDayOfMonth);
    }
    public static String getDayOfWeekName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "";
        }
    }
}
