/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class TimeUtils {
    private TimeUtils() {
    }

    public static String printDuration(double uptime) {
        DecimalFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
        DecimalFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.ENGLISH));
        if ((uptime /= 1000.0) < 60.0) {
            return fmtD.format(uptime) + " seconds";
        }
        if ((uptime /= 60.0) < 60.0) {
            long minutes = (long)uptime;
            String s2 = fmtI.format(minutes) + (minutes > 1L ? " minutes" : " minute");
            return s2;
        }
        if ((uptime /= 60.0) < 24.0) {
            long hours = (long)uptime;
            long minutes = (long)((uptime - (double)hours) * 60.0);
            String s3 = fmtI.format(hours) + (hours > 1L ? " hours" : " hour");
            if (minutes != 0L) {
                s3 = s3 + " " + fmtI.format(minutes) + (minutes > 1L ? " minutes" : " minute");
            }
            return s3;
        }
        long days = (long)(uptime /= 24.0);
        long hours = (long)((uptime - (double)days) * 24.0);
        String s4 = fmtI.format(days) + (days > 1L ? " days" : " day");
        if (hours != 0L) {
            s4 = s4 + " " + fmtI.format(hours) + (hours > 1L ? " hours" : " hour");
        }
        return s4;
    }
}

