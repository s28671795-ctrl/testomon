/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.hibernate.management.api;

import net.sf.ehcache.hibernate.management.api.EhcacheStats;
import net.sf.ehcache.hibernate.management.api.HibernateStats;

public interface EhcacheHibernateMBean
extends EhcacheStats,
HibernateStats {
    public boolean isHibernateStatisticsSupported();
}

