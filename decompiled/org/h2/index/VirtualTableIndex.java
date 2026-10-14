/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.table.IndexColumn;
import org.h2.table.VirtualTable;

public abstract class VirtualTableIndex
extends Index {
    protected VirtualTableIndex(VirtualTable virtualTable, String string, IndexColumn[] indexColumnArray) {
        super(virtualTable, 0, string, indexColumnArray, 0, IndexType.createNonUnique(true));
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public void add(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public void remove(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public void remove(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public void truncate(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public boolean needRebuild() {
        return false;
    }

    @Override
    public void checkRename() {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return this.table.getRowCount(sessionLocal);
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.table.getRowCountApproximation(sessionLocal);
    }
}

