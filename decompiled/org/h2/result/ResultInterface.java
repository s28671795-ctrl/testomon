/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.engine.Session;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public interface ResultInterface
extends AutoCloseable {
    public void reset();

    public Value[] currentRow();

    public boolean next();

    public long getRowId();

    public boolean isAfterLast();

    public int getVisibleColumnCount();

    public long getRowCount();

    public boolean hasNext();

    public boolean needToClose();

    @Override
    public void close();

    public String getAlias(int var1);

    public String getSchemaName(int var1);

    public String getTableName(int var1);

    public String getColumnName(int var1);

    public TypeInfo getColumnType(int var1);

    public boolean isIdentity(int var1);

    public int getNullable(int var1);

    public void setFetchSize(int var1);

    public int getFetchSize();

    public boolean isLazy();

    public boolean isClosed();

    public ResultInterface createShallowCopy(Session var1);
}

