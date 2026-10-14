/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.access.AccessType
 *  org.hibernate.cache.access.CollectionRegionAccessStrategy
 *  org.hibernate.cache.access.EntityRegionAccessStrategy
 */
package net.sf.ehcache.hibernate.strategy;

import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

public interface EhcacheAccessStrategyFactory {
    public EntityRegionAccessStrategy createEntityRegionAccessStrategy(EhcacheEntityRegion var1, AccessType var2);

    public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(EhcacheCollectionRegion var1, AccessType var2);
}

