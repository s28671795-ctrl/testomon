/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CollectionRegion
 *  org.hibernate.cache.access.CollectionRegionAccessStrategy
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate.strategy;

import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.strategy.AbstractReadWriteEhcacheAccessStrategy;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;

public class ReadWriteEhcacheCollectionRegionAccessStrategy
extends AbstractReadWriteEhcacheAccessStrategy<EhcacheCollectionRegion>
implements CollectionRegionAccessStrategy {
    public ReadWriteEhcacheCollectionRegionAccessStrategy(EhcacheCollectionRegion region, Settings settings) {
        super(region, settings);
    }

    public CollectionRegion getRegion() {
        return (CollectionRegion)this.region;
    }
}

