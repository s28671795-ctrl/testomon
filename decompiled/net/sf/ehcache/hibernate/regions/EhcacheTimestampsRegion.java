/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.TimestampsRegion
 */
package net.sf.ehcache.hibernate.regions;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.regions.EhcacheGeneralDataRegion;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import org.hibernate.cache.TimestampsRegion;

public class EhcacheTimestampsRegion
extends EhcacheGeneralDataRegion
implements TimestampsRegion {
    public EhcacheTimestampsRegion(EhcacheAccessStrategyFactory accessStrategyFactory, Ehcache underlyingCache, Properties properties) {
        super(accessStrategyFactory, underlyingCache, properties);
    }
}

