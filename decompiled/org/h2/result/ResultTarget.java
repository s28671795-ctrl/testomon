/*
 * Decompiled with CFR 0.152.
 */
package org.h2.result;

import org.h2.value.Value;

public interface ResultTarget {
    public void addRow(Value ... var1);

    public long getRowCount();

    public void limitsWereApplied();
}

