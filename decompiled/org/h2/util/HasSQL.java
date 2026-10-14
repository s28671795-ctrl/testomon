/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

public interface HasSQL {
    public static final int QUOTE_ONLY_WHEN_REQUIRED = 1;
    public static final int REPLACE_LOBS_FOR_TRACE = 2;
    public static final int NO_CASTS = 4;
    public static final int ADD_PLAN_INFORMATION = 8;
    public static final int DEFAULT_SQL_FLAGS = 0;
    public static final int TRACE_SQL_FLAGS = 3;

    default public String getTraceSQL() {
        return this.getSQL(3);
    }

    default public String getSQL(int n) {
        return this.getSQL(new StringBuilder(), n).toString();
    }

    public StringBuilder getSQL(StringBuilder var1, int var2);
}

