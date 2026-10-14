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
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import net.sf.ehcache.hibernate.strategy.NonStrictReadWriteEhcacheCollectionRegionAccessStrategy;
import net.sf.ehcache.hibernate.strategy.NonStrictReadWriteEhcacheEntityRegionAccessStrategy;
import net.sf.ehcache.hibernate.strategy.ReadOnlyEhcacheCollectionRegionAccessStrategy;
import net.sf.ehcache.hibernate.strategy.ReadOnlyEhcacheEntityRegionAccessStrategy;
import net.sf.ehcache.hibernate.strategy.ReadWriteEhcacheCollectionRegionAccessStrategy;
import net.sf.ehcache.hibernate.strategy.ReadWriteEhcacheEntityRegionAccessStrategy;
import net.sf.ehcache.hibernate.strategy.TransactionalEhcacheCollectionRegionAccessStrategy;
import net.sf.ehcache.hibernate.strategy.TransactionalEhcacheEntityRegionAccessStrategy;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhcacheAccessStrategyFactoryImpl
implements EhcacheAccessStrategyFactory {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheAccessStrategyFactoryImpl.class);

    @Override
    public EntityRegionAccessStrategy createEntityRegionAccessStrategy(EhcacheEntityRegion entityRegion, AccessType accessType) {
        if (AccessType.READ_ONLY.equals(accessType)) {
            if (entityRegion.getCacheDataDescription().isMutable()) {
                LOG.warn("read-only cache configured for mutable entity [" + entityRegion.getName() + "]");
            }
            return new ReadOnlyEhcacheEntityRegionAccessStrategy(entityRegion, entityRegion.getSettings());
        }
        if (AccessType.READ_WRITE.equals(accessType)) {
            return new ReadWriteEhcacheEntityRegionAccessStrategy(entityRegion, entityRegion.getSettings());
        }
        if (AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
            return new NonStrictReadWriteEhcacheEntityRegionAccessStrategy(entityRegion, entityRegion.getSettings());
        }
        if (AccessType.TRANSACTIONAL.equals(accessType)) {
            return new TransactionalEhcacheEntityRegionAccessStrategy(entityRegion, entityRegion.getEhcache(), entityRegion.getSettings());
        }
        throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
    }

    @Override
    public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(EhcacheCollectionRegion collectionRegion, AccessType accessType) {
        if (AccessType.READ_ONLY.equals(accessType)) {
            if (collectionRegion.getCacheDataDescription().isMutable()) {
                LOG.warn("read-only cache configured for mutable entity [" + collectionRegion.getName() + "]");
            }
            return new ReadOnlyEhcacheCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getSettings());
        }
        if (AccessType.READ_WRITE.equals(accessType)) {
            return new ReadWriteEhcacheCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getSettings());
        }
        if (AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
            return new NonStrictReadWriteEhcacheCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getSettings());
        }
        if (AccessType.TRANSACTIONAL.equals(accessType)) {
            return new TransactionalEhcacheCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getEhcache(), collectionRegion.getSettings());
        }
        throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
    }
}

