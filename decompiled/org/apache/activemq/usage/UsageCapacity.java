/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

public interface UsageCapacity {
    public boolean isLimit(long var1);

    public long getLimit();

    public void setLimit(long var1);
}

