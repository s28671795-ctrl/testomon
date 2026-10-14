/*
 * Decompiled with CFR 0.152.
 */
package org.h2.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import org.h2.expression.ParameterInterface;
import org.h2.message.TraceWriter;
import org.h2.util.StringUtils;

public final class Trace {
    public static final int COMMAND = 0;
    public static final int CONSTRAINT = 1;
    public static final int DATABASE = 2;
    public static final int FUNCTION = 3;
    public static final int FILE_LOCK = 4;
    public static final int INDEX = 5;
    public static final int JDBC = 6;
    public static final int LOCK = 7;
    public static final int SCHEMA = 8;
    public static final int SEQUENCE = 9;
    public static final int SETTING = 10;
    public static final int TABLE = 11;
    public static final int TRIGGER = 12;
    public static final int USER = 13;
    public static final int JDBCX = 14;
    static final String[] MODULE_NAMES = new String[]{"command", "constraint", "database", "function", "fileLock", "index", "jdbc", "lock", "schema", "sequence", "setting", "table", "trigger", "user", "JDBCX"};
    private final TraceWriter traceWriter;
    private final String module;
    private final String lineSeparator;
    private int traceLevel = -1;

    Trace(TraceWriter traceWriter, int n) {
        this(traceWriter, MODULE_NAMES[n]);
    }

    Trace(TraceWriter traceWriter, String string) {
        this.traceWriter = traceWriter;
        this.module = string;
        this.lineSeparator = System.lineSeparator();
    }

    public void setLevel(int n) {
        this.traceLevel = n;
    }

    private boolean isEnabled(int n) {
        if (this.traceLevel == -1) {
            return this.traceWriter.isEnabled(n);
        }
        return n <= this.traceLevel;
    }

    public boolean isInfoEnabled() {
        return this.isEnabled(2);
    }

    public boolean isDebugEnabled() {
        return this.isEnabled(3);
    }

    public void error(Throwable throwable, String string) {
        if (this.isEnabled(1)) {
            this.traceWriter.write(1, this.module, string, throwable);
        }
    }

    public void error(Throwable throwable, String string, Object ... objectArray) {
        if (this.isEnabled(1)) {
            string = MessageFormat.format(string, objectArray);
            this.traceWriter.write(1, this.module, string, throwable);
        }
    }

    public void info(String string) {
        if (this.isEnabled(2)) {
            this.traceWriter.write(2, this.module, string, null);
        }
    }

    public void info(String string, Object ... objectArray) {
        if (this.isEnabled(2)) {
            string = MessageFormat.format(string, objectArray);
            this.traceWriter.write(2, this.module, string, null);
        }
    }

    void info(Throwable throwable, String string) {
        if (this.isEnabled(2)) {
            this.traceWriter.write(2, this.module, string, throwable);
        }
    }

    public static String formatParams(ArrayList<? extends ParameterInterface> arrayList) {
        if (arrayList.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        for (ParameterInterface parameterInterface : arrayList) {
            if (!parameterInterface.isValueSet()) continue;
            stringBuilder.append(n == 0 ? " {" : ", ").append(++n).append(": ").append(parameterInterface.getParamValue().getTraceSQL());
        }
        if (n != 0) {
            stringBuilder.append('}');
        }
        return stringBuilder.toString();
    }

    public void infoSQL(String string, String string2, long l, long l2) {
        if (!this.isEnabled(2)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + string2.length() + 20);
        stringBuilder.append(this.lineSeparator).append("/*SQL");
        boolean bl = false;
        if (string2.length() > 0) {
            bl = true;
            stringBuilder.append(" l:").append(string.length());
        }
        if (l > 0L) {
            bl = true;
            stringBuilder.append(" #:").append(l);
        }
        if (l2 > 0L) {
            bl = true;
            stringBuilder.append(" t:").append(l2);
        }
        if (!bl) {
            stringBuilder.append(' ');
        }
        stringBuilder.append("*/");
        StringUtils.javaEncode(string, stringBuilder, false);
        StringUtils.javaEncode(string2, stringBuilder, false);
        stringBuilder.append(';');
        string = stringBuilder.toString();
        this.traceWriter.write(2, this.module, string, null);
    }

    public void debug(String string, Object ... objectArray) {
        if (this.isEnabled(3)) {
            string = MessageFormat.format(string, objectArray);
            this.traceWriter.write(3, this.module, string, null);
        }
    }

    public void debug(String string) {
        if (this.isEnabled(3)) {
            this.traceWriter.write(3, this.module, string, null);
        }
    }

    public void debug(Throwable throwable, String string) {
        if (this.isEnabled(3)) {
            this.traceWriter.write(3, this.module, string, throwable);
        }
    }

    public void infoCode(String string) {
        if (this.isEnabled(2)) {
            this.traceWriter.write(2, this.module, this.lineSeparator + "/**/" + string, null);
        }
    }

    void debugCode(String string) {
        if (this.isEnabled(3)) {
            this.traceWriter.write(3, this.module, this.lineSeparator + "/**/" + string, null);
        }
    }
}

