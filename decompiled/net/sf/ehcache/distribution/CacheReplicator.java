/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import net.sf.ehcache.event.CacheEventListener;

public interface CacheReplicator
extends CacheEventListener {
    public boolean isReplicateUpdatesViaCopy();

    public boolean notAlive();

    public boolean alive();
}

