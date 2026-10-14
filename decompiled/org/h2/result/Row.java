/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import java.util.Arrays;
import org.h2.result.DefaultRow;
import org.h2.result.SearchRow;
import org.h2.value.Value;

public abstract class Row
extends SearchRow {
    public static Row get(Value[] valueArray, int n) {
        return new DefaultRow(valueArray, n);
    }

    public static Row get(Value[] valueArray, int n, long l) {
        DefaultRow defaultRow = new DefaultRow(valueArray, n);
        defaultRow.setKey(l);
        return defaultRow;
    }

    public abstract Value[] getValueList();

    public boolean hasSameValues(Row row) {
        return Arrays.equals(this.getValueList(), row.getValueList());
    }

    public boolean hasSharedData(Row row) {
        return false;
    }
}

