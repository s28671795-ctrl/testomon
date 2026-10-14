/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.text.SimpleDateFormat;
import java.util.Date;

enum DateType {
    FULL("yyyy-MM-dd HH:mm:ss.S z"),
    LONG("yyyy-MM-dd HH:mm:ss z"),
    NORMAL("yyyy-MM-dd z"),
    SHORT("yyyy-MM-dd");

    private DateFormat format;

    private DateType(String format) {
        this.format = new DateFormat(format);
    }

    private DateFormat getFormat() {
        return this.format;
    }

    public static String getText(Date date) throws Exception {
        DateFormat format = FULL.getFormat();
        return format.getText(date);
    }

    public static Date getDate(String text) throws Exception {
        DateType type = DateType.getType(text);
        DateFormat format = type.getFormat();
        return format.getDate(text);
    }

    public static DateType getType(String text) {
        int length = text.length();
        if (length > 23) {
            return FULL;
        }
        if (length > 20) {
            return LONG;
        }
        if (length > 11) {
            return NORMAL;
        }
        return SHORT;
    }

    private static class DateFormat {
        private SimpleDateFormat format;

        public DateFormat(String format) {
            this.format = new SimpleDateFormat(format);
        }

        public synchronized String getText(Date date) throws Exception {
            return this.format.format(date);
        }

        public synchronized Date getDate(String text) throws Exception {
            return this.format.parse(text);
        }
    }
}

