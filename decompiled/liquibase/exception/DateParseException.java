/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import java.text.ParseException;

public class DateParseException
extends ParseException {
    private static final long serialVersionUID = 5140152882720200275L;

    public DateParseException(String s2) {
        super(s2, 0);
    }
}

