/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.CacheDataDescription
 *  org.hibernate.cache.CacheException
 *  org.hibernate.cache.CollectionRegion
 *  org.hibernate.cache.access.AccessType
 *  org.hibernate.cache.access.CollectionRegionAccessStrategy
 *  org.hibernate.cfg.Settings
 */
package net.sf.ehcache.hibernate.regions;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.regions.EhcacheTransactionalDataRegion;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhcacheCollectionRegion
extends EhcacheTransactionalDataRegion
implements CollectionRegion {
    private static final Logger LOG = LoggerFactory.getLogger(EhcacheEntityRegion.class);

    public EhcacheCollectionRegion(EhcacheAccessStrategyFactory accessStrategyFactory, Ehcache underlyingCache, Settings settings, CacheDataDescription metadata, Properties properties) {
        super(accessStrategyFactory, underlyingCache, settings, metadata, properties);
    }

    public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return this.accessStrategyFactory.createCollectionRegionAccessStrategy(this, accessType);
    }
}

