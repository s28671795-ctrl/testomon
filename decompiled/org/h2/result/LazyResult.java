/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.result.FetchedResult;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public abstract class LazyResult
extends FetchedResult {
    private final SessionLocal session;
    private final Expression[] expressions;
    private boolean closed;
    private long limit;

    public LazyResult(SessionLocal sessionLocal, Expression[] expressionArray) {
        this.session = sessionLocal;
        this.expressions = expressionArray;
    }

    public void setLimit(long l) {
        this.limit = l;
    }

    @Override
    public boolean isLazy() {
        return true;
    }

    @Override
    public void reset() {
        if (this.closed) {
            throw DbException.getInternalError();
        }
        this.rowId = -1L;
        this.afterLast = false;
        this.currentRow = null;
        this.nextRow = null;
    }

    public boolean skip() {
        if (this.closed || this.afterLast) {
            return false;
        }
        this.currentRow = null;
        if (this.nextRow != null) {
            this.nextRow = null;
            return true;
        }
        if (this.skipNextRow()) {
            return true;
        }
        this.afterLast = true;
        return false;
    }

    @Override
    public boolean hasNext() {
        if (this.closed || this.afterLast) {
            return false;
        }
        if (this.nextRow == null && (this.limit <= 0L || this.rowId + 1L < this.limit)) {
            this.nextRow = this.fetchNextRow();
        }
        return this.nextRow != null;
    }

    protected abstract Value[] fetchNextRow();

    protected boolean skipNextRow() {
        return this.fetchNextRow() != null;
    }

    @Override
    public long getRowCount() {
        throw DbException.getUnsupportedException("Row count is unknown for lazy result.");
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public void close() {
        this.closed = true;
    }

    @Override
    public String getAlias(int n) {
        return this.expressions[n].getAlias(this.session, n);
    }

    @Override
    public String getSchemaName(int n) {
        return this.expressions[n].getSchemaName();
    }

    @Override
    public String getTableName(int n) {
        return this.expressions[n].getTableName();
    }

    @Override
    public String getColumnName(int n) {
        return this.expressions[n].getColumnName(this.session, n);
    }

    @Override
    public TypeInfo getColumnType(int n) {
        return this.expressions[n].getType();
    }

    @Override
    public boolean isIdentity(int n) {
        return this.expressions[n].isIdentity();
    }

    @Override
    public int getNullable(int n) {
        return this.expressions[n].getNullable();
    }

    @Override
    public void setFetchSize(int n) {
    }

    @Override
    public int getFetchSize() {
        return 1;
    }
}

