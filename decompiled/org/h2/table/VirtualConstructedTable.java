/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import org.h2.engine.SessionLocal;
import org.h2.index.Index;
import org.h2.index.VirtualConstructedTableIndex;
import org.h2.result.ResultInterface;
import org.h2.schema.Schema;
import org.h2.table.IndexColumn;
import org.h2.table.VirtualTable;

public abstract class VirtualConstructedTable
extends VirtualTable {
    protected VirtualConstructedTable(Schema schema, int n, String string) {
        super(schema, n, string);
    }

    public abstract ResultInterface getResult(SessionLocal var1);

    @Override
    public Index getScanIndex(SessionLocal sessionLocal) {
        return new VirtualConstructedTableIndex(this, IndexColumn.wrap(this.columns));
    }

    @Override
    public long getMaxDataModificationId() {
        return Long.MAX_VALUE;
    }
}

