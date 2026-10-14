/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IMAPDateFormat
extends SimpleDateFormat {
    public IMAPDateFormat() {
        super("dd-MMM-yyyy HH:mm:ss Z", Locale.US);
    }

    public StringBuffer format(Date date, StringBuffer buffer, FieldPosition position) {
        StringBuffer result = super.format(date, buffer, position);
        if (result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }
        return result;
    }

    public void setCalendar(Calendar calendar) {
        throw new UnsupportedOperationException();
    }

    public void setNumberFormat(NumberFormat format) {
        throw new UnsupportedOperationException();
    }
}

