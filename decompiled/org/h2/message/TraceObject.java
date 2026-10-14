/*
 * Decompiled with CFR 0.152.
 */
package org.h2.message;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerArray;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.util.StringUtils;

public abstract class TraceObject {
    protected static final int CALLABLE_STATEMENT = 0;
    protected static final int CONNECTION = 1;
    protected static final int DATABASE_META_DATA = 2;
    protected static final int PREPARED_STATEMENT = 3;
    protected static final int RESULT_SET = 4;
    protected static final int RESULT_SET_META_DATA = 5;
    protected static final int SAVEPOINT = 6;
    protected static final int STATEMENT = 8;
    protected static final int BLOB = 9;
    protected static final int CLOB = 10;
    protected static final int PARAMETER_META_DATA = 11;
    protected static final int DATA_SOURCE = 12;
    protected static final int XA_DATA_SOURCE = 13;
    protected static final int XID = 15;
    protected static final int ARRAY = 16;
    protected static final int SQLXML = 17;
    private static final int LAST = 18;
    private static final AtomicIntegerArray ID = new AtomicIntegerArray(18);
    private static final String[] PREFIX = new String[]{"call", "conn", "dbMeta", "prep", "rs", "rsMeta", "sp", "ex", "stat", "blob", "clob", "pMeta", "ds", "xads", "xares", "xid", "ar", "sqlxml"};
    private static final SQLException SQL_OOME = DbException.SQL_OOME;
    protected Trace trace;
    private int traceType;
    private int id;

    protected void setTrace(Trace trace, int n, int n2) {
        this.trace = trace;
        this.traceType = n;
        this.id = n2;
    }

    public int getTraceId() {
        return this.id;
    }

    public String getTraceObjectName() {
        return PREFIX[this.traceType] + this.id;
    }

    protected static int getNextId(int n) {
        return ID.getAndIncrement(n);
    }

    protected final boolean isDebugEnabled() {
        return this.trace.isDebugEnabled();
    }

    protected final boolean isInfoEnabled() {
        return this.trace.isInfoEnabled();
    }

    protected final void debugCodeAssign(String string, int n, int n2, String string2) {
        if (this.trace.isDebugEnabled()) {
            this.trace.debugCode(string + ' ' + PREFIX[n] + n2 + " = " + this.getTraceObjectName() + '.' + string2 + ';');
        }
    }

    protected final void debugCodeCall(String string) {
        if (this.trace.isDebugEnabled()) {
            this.trace.debugCode(this.getTraceObjectName() + '.' + string + "();");
        }
    }

    protected final void debugCodeCall(String string, long l) {
        if (this.trace.isDebugEnabled()) {
            this.trace.debugCode(this.getTraceObjectName() + '.' + string + '(' + l + ");");
        }
    }

    protected final void debugCodeCall(String string, String string2) {
        if (this.trace.isDebugEnabled()) {
            this.trace.debugCode(this.getTraceObjectName() + '.' + string + '(' + TraceObject.quote(string2) + ");");
        }
    }

    protected final void debugCode(String string) {
        if (this.trace.isDebugEnabled()) {
            this.trace.debugCode(this.getTraceObjectName() + '.' + string + ';');
        }
    }

    protected static String quote(String string) {
        return StringUtils.quoteJavaString(string);
    }

    protected static String quoteTime(Time time) {
        if (time == null) {
            return "null";
        }
        return "Time.valueOf(\"" + time.toString() + "\")";
    }

    protected static String quoteTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return "null";
        }
        return "Timestamp.valueOf(\"" + timestamp.toString() + "\")";
    }

    protected static String quoteDate(Date date) {
        if (date == null) {
            return "null";
        }
        return "Date.valueOf(\"" + date.toString() + "\")";
    }

    protected static String quoteBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return "null";
        }
        return "new BigDecimal(\"" + bigDecimal.toString() + "\")";
    }

    protected static String quoteBytes(byte[] byArray) {
        if (byArray == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder(byArray.length * 2 + 45).append("org.h2.util.StringUtils.convertHexToBytes(\"");
        return StringUtils.convertBytesToHex(stringBuilder, byArray).append("\")").toString();
    }

    protected static String quoteArray(String[] stringArray) {
        return StringUtils.quoteJavaStringArray(stringArray);
    }

    protected static String quoteIntArray(int[] nArray) {
        return StringUtils.quoteJavaIntArray(nArray);
    }

    protected static String quoteMap(Map<String, Class<?>> map) {
        if (map == null) {
            return "null";
        }
        if (map.size() == 0) {
            return "new Map()";
        }
        return "new Map() /* " + map.toString() + " */";
    }

    protected SQLException logAndConvert(Throwable throwable) {
        SQLException sQLException = null;
        try {
            sQLException = DbException.toSQLException(throwable);
            if (this.trace == null) {
                DbException.traceThrowable(sQLException);
            } else {
                int n = sQLException.getErrorCode();
                if (n >= 23000 && n < 24000) {
                    this.trace.info(sQLException, "exception");
                } else {
                    this.trace.error(sQLException, "exception");
                }
            }
        }
        catch (Throwable throwable2) {
            if (sQLException == null) {
                try {
                    sQLException = new SQLException("GeneralError", "HY000", 50000, throwable);
                }
                catch (NoClassDefFoundError | OutOfMemoryError error) {
                    return SQL_OOME;
                }
            }
            sQLException.addSuppressed(throwable2);
        }
        return sQLException;
    }

    protected SQLException unsupported(String string) {
        try {
            throw DbException.getUnsupportedException(string);
        }
        catch (Exception exception) {
            return this.logAndConvert(exception);
        }
    }
}

