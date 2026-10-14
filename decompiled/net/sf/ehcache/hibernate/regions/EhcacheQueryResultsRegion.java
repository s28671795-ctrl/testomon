/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.cache.QueryResultsRegion
 */
package net.sf.ehcache.hibernate.regions;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.regions.EhcacheGeneralDataRegion;
import net.sf.ehcache.hibernate.strategy.EhcacheAccessStrategyFactory;
import org.hibernate.cache.QueryResultsRegion;

public class EhcacheQueryResultsRegion
extends EhcacheGeneralDataRegion
implements QueryResultsRegion {
    public EhcacheQueryResultsRegion(EhcacheAccessStrategyFactory accessStrategyFactory, Ehcache underlyingCache, Properties properties) {
        super(accessStrategyFactory, underlyingCache, properties);
    }
}

