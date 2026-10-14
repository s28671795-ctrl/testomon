/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import org.apache.activemq.usage.UsageCapacity;

public class DefaultUsageCapacity
implements UsageCapacity {
    private long limit;

    @Override
    public boolean isLimit(long size) {
        return size >= this.limit;
    }

    @Override
    public final long getLimit() {
        return this.limit;
    }

    @Override
    public final void setLimit(long limit) {
        this.limit = limit;
    }
}

