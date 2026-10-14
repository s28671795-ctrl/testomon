/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.index.DualIndex;
import org.h2.index.Index;
import org.h2.table.Column;
import org.h2.table.TableType;
import org.h2.table.VirtualTable;

public class DualTable
extends VirtualTable {
    public static final String NAME = "DUAL";

    public DualTable(Database database) {
        super(database.getMainSchema(), 0, NAME);
        this.setColumns(new Column[0]);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(NAME);
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        return true;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return 1L;
    }

    @Override
    public TableType getTableType() {
        return TableType.SYSTEM_TABLE;
    }

    @Override
    public Index getScanIndex(SessionLocal sessionLocal) {
        return new DualIndex(this);
    }

    @Override
    public long getMaxDataModificationId() {
        return 0L;
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return 1L;
    }

    @Override
    public boolean isDeterministic() {
        return true;
    }
}

