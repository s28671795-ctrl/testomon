/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

public interface JdbcException {
    public int getErrorCode();

    public String getOriginalMessage();

    public String getSQL();

    public void setSQL(String var1);

    public String toString();
}

