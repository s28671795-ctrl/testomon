/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

public enum DefaultNullOrdering {
    LOW(2, 4),
    HIGH(4, 2),
    FIRST(2, 2),
    LAST(4, 4);

    private static final DefaultNullOrdering[] VALUES;
    private final int defaultAscNulls;
    private final int defaultDescNulls;
    private final int nullAsc;
    private final int nullDesc;

    public static DefaultNullOrdering valueOf(int n) {
        return VALUES[n];
    }

    private DefaultNullOrdering(int n2, int n3) {
        this.defaultAscNulls = n2;
        this.defaultDescNulls = n3;
        this.nullAsc = n2 == 2 ? -1 : 1;
        this.nullDesc = n3 == 2 ? -1 : 1;
    }

    public int addExplicitNullOrdering(int n) {
        if ((n & 6) == 0) {
            n |= (n & 1) == 0 ? this.defaultAscNulls : this.defaultDescNulls;
        }
        return n;
    }

    public int compareNull(boolean bl, int n) {
        if ((n & 2) != 0) {
            return bl ? -1 : 1;
        }
        if ((n & 4) != 0) {
            return bl ? 1 : -1;
        }
        if ((n & 1) == 0) {
            return bl ? this.nullAsc : -this.nullAsc;
        }
        return bl ? this.nullDesc : -this.nullDesc;
    }

    static {
        VALUES = DefaultNullOrdering.values();
    }
}

