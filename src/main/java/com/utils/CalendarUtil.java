package com.utils;

import java.util.HashMap;

public class CalendarUtil {

    public static HashMap<String, Integer> mapCalendarMonth() {

        HashMap<String, Integer> calendarMonthMap = new HashMap<>();
        calendarMonthMap.put("January", 1);
        calendarMonthMap.put("February", 2);
        calendarMonthMap.put("March", 3);
        calendarMonthMap.put("April", 4);
        calendarMonthMap.put("May", 5);
        calendarMonthMap.put("June", 6);
        calendarMonthMap.put("July", 7);
        calendarMonthMap.put("August", 8);
        calendarMonthMap.put("September", 9);
        calendarMonthMap.put("October", 10);
        calendarMonthMap.put("November", 11);
        calendarMonthMap.put("December", 12);

        return calendarMonthMap;


    }
}
