/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.xjc.runtime;

import java.util.Calendar;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;

public final class DataTypeAdapter {
    private DataTypeAdapter() {
    }

    public static Date parseDate(String s2) {
        if (s2 == null) {
            return null;
        }
        return DatatypeConverter.parseDate(s2).getTime();
    }

    public static String printDate(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        return DatatypeConverter.printDate(c);
    }

    public static Date parseTime(String s2) {
        if (s2 == null) {
            return null;
        }
        return DatatypeConverter.parseTime(s2).getTime();
    }

    public static String printTime(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        return DatatypeConverter.printTime(c);
    }

    public static Date parseDateTime(String s2) {
        if (s2 == null) {
            return null;
        }
        return DatatypeConverter.parseDateTime(s2).getTime();
    }

    public static String printDateTime(Date dt) {
        if (dt == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        return DatatypeConverter.printDateTime(c);
    }
}

