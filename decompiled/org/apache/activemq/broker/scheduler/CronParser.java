/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import javax.jms.MessageFormatException;

public class CronParser {
    private static final int NUMBER_TOKENS = 5;
    private static final int MINUTES = 0;
    private static final int HOURS = 1;
    private static final int DAY_OF_MONTH = 2;
    private static final int MONTH = 3;
    private static final int DAY_OF_WEEK = 4;

    public static long getNextScheduledTime(String cronEntry, long currentTime) throws MessageFormatException {
        int nextHour;
        int currentHours;
        long result = 0L;
        if (cronEntry == null || cronEntry.length() == 0) {
            return result;
        }
        if (cronEntry.equals("* * * * *")) {
            result = currentTime + 60000L;
            result = result / 60000L * 60000L;
            return result;
        }
        List<String> list = CronParser.tokenize(cronEntry);
        List<CronEntry> entries = CronParser.buildCronEntries(list);
        Calendar working = Calendar.getInstance();
        working.setTimeInMillis(currentTime);
        working.set(13, 0);
        CronEntry minutes = entries.get(0);
        CronEntry hours = entries.get(1);
        CronEntry dayOfMonth = entries.get(2);
        CronEntry month = entries.get(3);
        CronEntry dayOfWeek = entries.get(4);
        int timeToNextMinute = 60 - working.get(13);
        working.add(13, timeToNextMinute);
        int currentMinutes = working.get(12);
        if (!CronParser.isCurrent(minutes, currentMinutes)) {
            int nextMinutes = CronParser.getNext(minutes, currentMinutes, working);
            working.add(12, nextMinutes);
        }
        if (!CronParser.isCurrent(hours, currentHours = working.get(11))) {
            nextHour = CronParser.getNext(hours, currentHours, working);
            working.add(11, nextHour);
        }
        CronParser.doUpdateCurrentDay(working, dayOfMonth, dayOfWeek);
        CronParser.doUpdateCurrentMonth(working, month);
        CronParser.doUpdateCurrentDay(working, dayOfMonth, dayOfWeek);
        currentHours = working.get(11);
        if (!CronParser.isCurrent(hours, currentHours)) {
            nextHour = CronParser.getNext(hours, currentHours, working);
            working.add(11, nextHour);
        }
        if (!CronParser.isCurrent(minutes, currentMinutes = working.get(12))) {
            int nextMinutes = CronParser.getNext(minutes, currentMinutes, working);
            working.add(12, nextMinutes);
        }
        if ((result = working.getTimeInMillis()) <= currentTime) {
            throw new ArithmeticException("Unable to compute next scheduled exection time.");
        }
        return result;
    }

    protected static long doUpdateCurrentMonth(Calendar working, CronEntry month) throws MessageFormatException {
        int currentMonth = working.get(2) + 1;
        if (!CronParser.isCurrent(month, currentMonth)) {
            int nextMonth = CronParser.getNext(month, currentMonth, working);
            working.add(2, nextMonth);
            CronParser.resetToStartOfDay(working, 1);
            return working.getTimeInMillis();
        }
        return 0L;
    }

    protected static long doUpdateCurrentDay(Calendar working, CronEntry dayOfMonth, CronEntry dayOfWeek) throws MessageFormatException {
        int currentDayOfWeek = working.get(7) - 1;
        int currentDayOfMonth = working.get(5);
        if (!CronParser.isCurrent(dayOfWeek, currentDayOfWeek) || !CronParser.isCurrent(dayOfMonth, currentDayOfMonth)) {
            int nextWeekDay = Integer.MAX_VALUE;
            int nextCalendarDay = Integer.MAX_VALUE;
            if (!CronParser.isCurrent(dayOfWeek, currentDayOfWeek)) {
                nextWeekDay = CronParser.getNext(dayOfWeek, currentDayOfWeek, working);
            }
            if (!CronParser.isCurrent(dayOfMonth, currentDayOfMonth)) {
                nextCalendarDay = CronParser.getNext(dayOfMonth, currentDayOfMonth, working);
            }
            if (nextWeekDay < nextCalendarDay) {
                working.add(7, nextWeekDay);
            } else {
                working.add(5, nextCalendarDay);
            }
            CronParser.resetToStartOfDay(working, working.get(5));
            return working.getTimeInMillis();
        }
        return 0L;
    }

    public static void validate(String cronEntry) throws MessageFormatException {
        List<String> list = CronParser.tokenize(cronEntry);
        List<CronEntry> entries = CronParser.buildCronEntries(list);
        for (CronEntry e : entries) {
            CronParser.validate(e);
        }
    }

    static void validate(CronEntry entry) throws MessageFormatException {
        List<Integer> list = entry.currentWhen;
        if (list.isEmpty() || list.get(0) < entry.start || list.get(list.size() - 1) > entry.end) {
            throw new MessageFormatException("Invalid token: " + entry);
        }
    }

    static int getNext(CronEntry entry, int current, Calendar working) throws MessageFormatException {
        int result = 0;
        if (entry.currentWhen == null) {
            entry.currentWhen = CronParser.calculateValues(entry);
        }
        List<Integer> list = entry.currentWhen;
        int next = -1;
        for (Integer i : list) {
            if (i <= current) continue;
            next = i;
            break;
        }
        if (next != -1) {
            result = next - current;
        } else {
            int first = list.get(0);
            int fixedEnd = entry.end;
            if ("DayOfMonth".equals(entry.name)) {
                fixedEnd = working.getActualMaximum(5) + 1;
            }
            result = fixedEnd + first - entry.start - current;
            if (entry.name.equals("DayOfWeek") || entry.name.equals("Month")) {
                ++result;
            }
        }
        return result;
    }

    static boolean isCurrent(CronEntry entry, int current) throws MessageFormatException {
        boolean result = entry.currentWhen.contains(new Integer(current));
        return result;
    }

    protected static void resetToStartOfDay(Calendar target, int day) {
        target.set(5, day);
        target.set(11, 0);
        target.set(12, 0);
        target.set(13, 0);
    }

    static List<String> tokenize(String cron) throws IllegalArgumentException {
        StringTokenizer tokenize = new StringTokenizer(cron);
        ArrayList<String> result = new ArrayList<String>();
        while (tokenize.hasMoreTokens()) {
            result.add(tokenize.nextToken());
        }
        if (result.size() != 5) {
            throw new IllegalArgumentException("Not a valid cron entry - wrong number of tokens(" + result.size() + "): " + cron);
        }
        return result;
    }

    protected static List<Integer> calculateValues(CronEntry entry) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if (CronParser.isAll(entry.token)) {
            for (int i = entry.start; i <= entry.end; ++i) {
                result.add(i);
            }
        } else if (CronParser.isAStep(entry.token)) {
            int denominator = CronParser.getDenominator(entry.token);
            String numerator = CronParser.getNumerator(entry.token);
            CronEntry ce = new CronEntry(entry.name, numerator, entry.start, entry.end);
            List<Integer> list = CronParser.calculateValues(ce);
            for (Integer i : list) {
                if (i % denominator != 0) continue;
                result.add(i);
            }
        } else if (CronParser.isAList(entry.token)) {
            StringTokenizer tokenizer = new StringTokenizer(entry.token, ",");
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                CronEntry ce = new CronEntry(entry.name, str, entry.start, entry.end);
                List<Integer> list = CronParser.calculateValues(ce);
                result.addAll(list);
            }
        } else if (CronParser.isARange(entry.token)) {
            int index = entry.token.indexOf(45);
            int first = Integer.parseInt(entry.token.substring(0, index));
            int last = Integer.parseInt(entry.token.substring(index + 1));
            for (int i = first; i <= last; ++i) {
                result.add(i);
            }
        } else {
            int value = Integer.parseInt(entry.token);
            result.add(value);
        }
        Collections.sort(result);
        return result;
    }

    protected static boolean isARange(String token) {
        return token != null && token.indexOf(45) >= 0;
    }

    protected static boolean isAStep(String token) {
        return token != null && token.indexOf(47) >= 0;
    }

    protected static boolean isAList(String token) {
        return token != null && token.indexOf(44) >= 0;
    }

    protected static boolean isAll(String token) {
        return token != null && token.length() == 1 && (token.charAt(0) == '*' || token.charAt(0) == '?');
    }

    protected static int getDenominator(String token) {
        int result = 0;
        int index = token.indexOf(47);
        String str = token.substring(index + 1);
        result = Integer.parseInt(str);
        return result;
    }

    protected static String getNumerator(String token) {
        int index = token.indexOf(47);
        String str = token.substring(0, index);
        return str;
    }

    static List<CronEntry> buildCronEntries(List<String> tokens) {
        ArrayList<CronEntry> result = new ArrayList<CronEntry>();
        CronEntry minutes = new CronEntry("Minutes", tokens.get(0), 0, 60);
        minutes.currentWhen = CronParser.calculateValues(minutes);
        result.add(minutes);
        CronEntry hours = new CronEntry("Hours", tokens.get(1), 0, 24);
        hours.currentWhen = CronParser.calculateValues(hours);
        result.add(hours);
        CronEntry dayOfMonth = new CronEntry("DayOfMonth", tokens.get(2), 1, 32);
        dayOfMonth.currentWhen = CronParser.calculateValues(dayOfMonth);
        result.add(dayOfMonth);
        CronEntry month = new CronEntry("Month", tokens.get(3), 1, 12);
        month.currentWhen = CronParser.calculateValues(month);
        result.add(month);
        CronEntry dayOfWeek = new CronEntry("DayOfWeek", tokens.get(4), 0, 6);
        dayOfWeek.currentWhen = CronParser.calculateValues(dayOfWeek);
        result.add(dayOfWeek);
        return result;
    }

    static class CronEntry {
        final String name;
        final String token;
        final int start;
        final int end;
        List<Integer> currentWhen;

        CronEntry(String name, String token, int start2, int end) {
            this.name = name;
            this.token = token;
            this.start = start2;
            this.end = end;
        }

        public String toString() {
            return this.name + ":" + this.token;
        }
    }
}

