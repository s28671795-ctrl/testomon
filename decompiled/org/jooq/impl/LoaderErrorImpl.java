/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.LoaderError;
import org.jooq.Query;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.Tools;

final class LoaderErrorImpl
implements LoaderError {
    private final DataAccessException exception;
    private final int rowIndex;
    private final String[] row;
    private final Query query;

    LoaderErrorImpl(DataAccessException exception, Object[] row, int rowIndex, Query query) {
        this.exception = exception;
        this.row = LoaderErrorImpl.strings(row);
        this.rowIndex = rowIndex;
        this.query = query;
    }

    private static String[] strings(Object[] row) {
        if (row == null) {
            return null;
        }
        return Tools.map(row, o -> o == null ? null : o.toString(), String[]::new);
    }

    @Override
    public DataAccessException exception() {
        return this.exception;
    }

    @Override
    public int rowIndex() {
        return this.rowIndex;
    }

    @Override
    public String[] row() {
        return this.row;
    }

    @Override
    public Query query() {
        return this.query;
    }
}

