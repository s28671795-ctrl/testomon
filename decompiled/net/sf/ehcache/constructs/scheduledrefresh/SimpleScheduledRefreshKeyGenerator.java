/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.io.Serializable;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshKeyGenerator;

public class SimpleScheduledRefreshKeyGenerator<K extends Serializable>
implements ScheduledRefreshKeyGenerator<K> {
    @Override
    public Iterable<K> generateKeys(Ehcache cache) {
        return cache.getKeys();
    }
}

