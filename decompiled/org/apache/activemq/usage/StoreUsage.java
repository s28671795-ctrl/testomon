/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.usage;

import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.usage.PercentLimitUsage;

public class StoreUsage
extends PercentLimitUsage<StoreUsage> {
    private PersistenceAdapter store;

    public StoreUsage() {
        super(null, null, 1.0f);
    }

    public StoreUsage(String name, PersistenceAdapter store) {
        super(null, name, 1.0f);
        this.store = store;
        this.updateLimitBasedOnPercent();
    }

    public StoreUsage(StoreUsage parent, String name) {
        super(parent, name, 1.0f);
        this.store = parent.store;
        this.updateLimitBasedOnPercent();
    }

    @Override
    protected long retrieveUsage() {
        if (this.store == null) {
            return 0L;
        }
        return this.store.size();
    }

    public PersistenceAdapter getStore() {
        return this.store;
    }

    public void setStore(PersistenceAdapter store) {
        this.store = store;
        if (this.percentLimit > 0 && store != null) {
            this.updateLimitBasedOnPercent();
        } else {
            this.onLimitChange();
        }
    }

    @Override
    public int getPercentUsage() {
        this.usageLock.writeLock().lock();
        try {
            this.percentUsage = this.caclPercentUsage();
            int n = super.getPercentUsage();
            return n;
        }
        finally {
            this.usageLock.writeLock().unlock();
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

    public StoreUsage copy() {
        StoreUsage storeUsage = new StoreUsage();
        storeUsage.name = this.name;
        storeUsage.parent = this.parent;
        storeUsage.total = this.total;
        storeUsage.percentLimit = this.percentLimit;
        storeUsage.getLimiter().setLimit(this.getLimiter().getLimit());
        return storeUsage;
    }
}

