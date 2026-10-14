/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.util.Objects;
import org.h2.engine.NullsDistinct;

public class IndexType {
    private boolean primaryKey;
    private boolean persistent;
    private boolean hash;
    private boolean scan;
    private boolean spatial;
    private boolean belongsToConstraint;
    private NullsDistinct nullsDistinct;

    public static IndexType createPrimaryKey(boolean bl, boolean bl2) {
        IndexType indexType = new IndexType();
        indexType.primaryKey = true;
        indexType.persistent = bl;
        indexType.hash = bl2;
        return indexType;
    }

    public static IndexType createUnique(boolean bl, boolean bl2, int n, NullsDistinct nullsDistinct) {
        IndexType indexType = new IndexType();
        indexType.persistent = bl;
        indexType.hash = bl2;
        indexType.nullsDistinct = n == 1 && nullsDistinct == NullsDistinct.ALL_DISTINCT ? NullsDistinct.DISTINCT : Objects.requireNonNull(nullsDistinct);
        return indexType;
    }

    public static IndexType createNonUnique(boolean bl) {
        return IndexType.createNonUnique(bl, false, false);
    }

    public static IndexType createNonUnique(boolean bl, boolean bl2, boolean bl3) {
        IndexType indexType = new IndexType();
        indexType.persistent = bl;
        indexType.hash = bl2;
        indexType.spatial = bl3;
        return indexType;
    }

    public static IndexType createScan(boolean bl) {
        IndexType indexType = new IndexType();
        indexType.persistent = bl;
        indexType.scan = true;
        return indexType;
    }

    public void setBelongsToConstraint(boolean bl) {
        this.belongsToConstraint = bl;
    }

    public boolean getBelongsToConstraint() {
        return this.belongsToConstraint;
    }

    public boolean isHash() {
        return this.hash;
    }

    public boolean isSpatial() {
        return this.spatial;
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    public boolean isPrimaryKey() {
        return this.primaryKey;
    }

    public boolean isUnique() {
        return this.primaryKey || this.nullsDistinct != null;
    }

    public String getSQL(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.primaryKey) {
            stringBuilder.append("PRIMARY KEY");
            if (this.hash) {
                stringBuilder.append(" HASH");
            }
        } else {
            if (this.nullsDistinct != null) {
                stringBuilder.append("UNIQUE ");
                if (bl) {
                    this.nullsDistinct.getSQL(stringBuilder, 0).append(' ');
                }
            }
            if (this.hash) {
                stringBuilder.append("HASH ");
            }
            if (this.spatial) {
                stringBuilder.append("SPATIAL ");
            }
            stringBuilder.append("INDEX");
        }
        return stringBuilder.toString();
    }

    public boolean isScan() {
        return this.scan;
    }

    public NullsDistinct getNullsDistinct() {
        return this.nullsDistinct;
    }

    public NullsDistinct getEffectiveNullsDistinct() {
        return this.nullsDistinct != null ? this.nullsDistinct : (this.primaryKey ? NullsDistinct.NOT_DISTINCT : null);
    }
}

