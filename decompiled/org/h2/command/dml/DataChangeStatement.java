/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.Prepared;
import org.h2.engine.SessionLocal;
import org.h2.result.ResultInterface;
import org.h2.result.ResultTarget;
import org.h2.table.DataChangeDeltaTable;
import org.h2.table.Table;

public abstract class DataChangeStatement
extends Prepared {
    private boolean isPrepared;

    protected DataChangeStatement(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public final void prepare() {
        if (this.isPrepared) {
            return;
        }
        this.doPrepare();
        this.isPrepared = true;
    }

    abstract void doPrepare();

    public abstract String getStatementName();

    public abstract Table getTable();

    @Override
    public final boolean isTransactional() {
        return true;
    }

    @Override
    public final ResultInterface queryMeta() {
        return null;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public final long update() {
        return this.update(null, null);
    }

    public abstract long update(ResultTarget var1, DataChangeDeltaTable.ResultOption var2);
}

