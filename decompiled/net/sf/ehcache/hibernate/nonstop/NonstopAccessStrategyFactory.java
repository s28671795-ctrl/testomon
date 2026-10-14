/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.access.AccessType
 *  org.hibernate.cache.access.CollectionRegionAccessStrategy
 *  org.hibernate.cache.access.EntityRegionAccessStrategy
 */
package net.sf.ehcache.hibernate.nonstop;

import net.sf.ehcache.hibernate.nonstop.HibernateNonstopCacheExceptionHandler;
import net.sf.ehcache.hibernate.nonstop.NonstopAwareCollectionRegionAccessStrategy;
import net.sf.ehcache.hibernate.nonstop.NonstopAwareEntityRegionAccessStrategy;
import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonstopAccessStrategyFactory
implements EhcacheAccessStrategyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(NonstopAccessStrategyFactory.class);
    private final EhcacheAccessStrategyFactory actualFactory;

    public NonstopAccessStrategyFactory(EhcacheAccessStrategyFactory actualFactory) {
        this.actualFactory = actualFactory;
    }

    @Override
    public EntityRegionAccessStrategy createEntityRegionAccessStrategy(EhcacheEntityRegion entityRegion, AccessType accessType) {
        return new NonstopAwareEntityRegionAccessStrategy(this.actualFactory.createEntityRegionAccessStrategy(entityRegion, accessType), HibernateNonstopCacheExceptionHandler.getInstance());
    }

    @Override
    public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(EhcacheCollectionRegion collectionRegion, AccessType accessType) {
        return new NonstopAwareCollectionRegionAccessStrategy(this.actualFactory.createCollectionRegionAccessStrategy(collectionRegion, accessType), HibernateNonstopCacheExceptionHandler.getInstance());
    }
}

