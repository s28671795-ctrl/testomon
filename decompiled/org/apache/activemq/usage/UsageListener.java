/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import org.apache.activemq.usage.Usage;

public interface UsageListener {
    public void onUsageChanged(Usage var1, int var2, int var3);
}

