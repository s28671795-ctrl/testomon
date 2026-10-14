/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.schema.Schema;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableType;

public abstract class VirtualTable
extends Table {
    protected VirtualTable(Schema schema, int n, String string) {
        super(schema, n, string, false, true);
    }

    @Override
    public void close(SessionLocal sessionLocal) {
    }

    @Override
    public Index addIndex(SessionLocal sessionLocal, String string, int n, IndexColumn[] indexColumnArray, int n2, IndexType indexType, boolean bl, String string2) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public boolean isInsertable() {
        return false;
    }

    @Override
    public void removeRow(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public long truncate(SessionLocal sessionLocal) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public void addRow(SessionLocal sessionLocal, Row row) {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public void checkSupportAlter() {
        throw DbException.getUnsupportedException("Virtual table");
    }

    @Override
    public TableType getTableType() {
        return null;
    }

    @Override
    public ArrayList<Index> getIndexes() {
        return null;
    }

    @Override
    public boolean canReference() {
        return false;
    }

    @Override
    public boolean canDrop() {
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public String getCreateSQL() {
        return null;
    }

    @Override
    public void checkRename() {
        throw DbException.getUnsupportedException("Virtual table");
    }
}

