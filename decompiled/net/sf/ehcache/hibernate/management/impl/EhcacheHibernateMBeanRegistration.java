/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.hibernate.SessionFactory
 */
package net.sf.ehcache.hibernate.management.impl;

import java.util.Properties;
import net.sf.ehcache.CacheManager;
import org.hibernate.SessionFactory;

public interface EhcacheHibernateMBeanRegistration {
    public void registerMBeanForCacheManager(CacheManager var1, Properties var2) throws Exception;

    public void enableHibernateStatisticsSupport(SessionFactory var1);
}

