/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.io.Serializable;
import net.sf.ehcache.Ehcache;

public interface ScheduledRefreshKeyGenerator<K extends Serializable> {
    public Iterable<K> generateKeys(Ehcache var1);
}

