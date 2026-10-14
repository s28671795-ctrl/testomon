/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobSupport {
    public static String getDateTime(long value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(value);
        return dateFormat.format(date);
    }

    public static long getDataTime(String value) throws Exception {
        SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dfm.parse(value);
        return date.getTime();
    }
}

