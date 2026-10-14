/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import org.apache.activemq.store.PListStore;
import org.apache.activemq.usage.PercentLimitUsage;

public class TempUsage
extends PercentLimitUsage<TempUsage> {
    private PListStore store;

    public TempUsage() {
        super(null, null, 1.0f);
    }

    public TempUsage(String name, PListStore store) {
        super(null, name, 1.0f);
        this.store = store;
        this.updateLimitBasedOnPercent();
    }

    public TempUsage(TempUsage parent, String name) {
        super(parent, name, 1.0f);
        this.store = parent.store;
        this.updateLimitBasedOnPercent();
    }

    @Override
    public int getPercentUsage() {
        if (this.store != null) {
            this.usageLock.writeLock().lock();
            try {
                this.percentUsage = this.caclPercentUsage();
            }
            finally {
                this.usageLock.writeLock().unlock();
            }
        }
        return super.getPercentUsage();
    }

    @Override
    protected long retrieveUsage() {
        if (this.store == null) {
            return 0L;
        }
        return this.store.size();
    }

    public PListStore getStore() {
        return this.store;
    }

    public void setStore(PListStore store) {
        this.store = store;
        if (this.percentLimit > 0 && store != null) {
            this.updateLimitBasedOnPercent();
        } else {
            this.onLimitChange();
        }
    }

    @Override
    protected void updateLimitBasedOnPercent() {
        this.usageLock.writeLock().lock();
        try {
            this.percentLimitFromFile(this.store != null ? this.store.getDirectory() : null);
        }
        finally {
            this.usageLock.writeLock().unlock();
        }
    }
}

