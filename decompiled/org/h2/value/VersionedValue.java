/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

public class VersionedValue<T> {
    protected VersionedValue() {
    }

    public boolean isCommitted() {
        return true;
    }

    public long getOperationId() {
        return 0L;
    }

    public T getCurrentValue() {
        return (T)this;
    }

    public T getCommittedValue() {
        return (T)this;
    }
}

