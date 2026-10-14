/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.util.List;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.event.CacheManagerEventListener;

public interface CacheManagerPeerListener
extends CacheManagerEventListener {
    public List getBoundCachePeers();

    public String getUniqueResourceIdentifier();

    public void attemptResolutionOfUniqueResourceConflict() throws IllegalStateException, CacheException;

    public String getScheme();
}

