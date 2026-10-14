/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.ColumnMetadata;
import java.util.List;

public interface RowMetadata {
    public ColumnMetadata getColumnMetadata(int var1);

    public ColumnMetadata getColumnMetadata(String var1);

    public List<? extends ColumnMetadata> getColumnMetadatas();

    default public boolean contains(String columnName) {
        for (ColumnMetadata columnMetadata : this.getColumnMetadatas()) {
            if (!columnMetadata.getName().equalsIgnoreCase(columnName)) continue;
            return true;
        }
        return false;
    }
}

