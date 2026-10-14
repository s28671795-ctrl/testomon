/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.db;

import java.util.List;
import org.h2.index.Index;
import org.h2.index.IndexType;
import org.h2.mvstore.MVMap;
import org.h2.result.Row;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.value.VersionedValue;

public abstract class MVIndex<K, V>
extends Index {
    protected MVIndex(Table table, int n, String string, IndexColumn[] indexColumnArray, int n2, IndexType indexType) {
        super(table, n, string, indexColumnArray, n2, indexType);
    }

    public abstract void addRowsToBuffer(List<Row> var1, String var2);

    public abstract void addBufferedRows(List<String> var1);

    public abstract MVMap<K, VersionedValue<V>> getMVMap();
}

