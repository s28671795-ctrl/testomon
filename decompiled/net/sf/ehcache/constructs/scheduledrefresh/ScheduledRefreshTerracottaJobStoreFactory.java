/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.quartz.TerracottaJobStore
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshJobStorePropertiesFactory;
import org.terracotta.quartz.TerracottaJobStore;

public class ScheduledRefreshTerracottaJobStoreFactory
implements ScheduledRefreshJobStorePropertiesFactory {
    @Override
    public Properties jobStoreProperties(Ehcache underlyingCache, ScheduledRefreshConfiguration config) {
        Properties p = new Properties(config.getExcessProperties());
        p.put("org.quartz.jobStore.class", TerracottaJobStore.class.getName());
        p.put("org.quartz.scheduler.instanceId", "AUTO");
        p.setProperty("org.quartz.jobStore.tcConfigUrl", config.getTerracottaConfigUrl());
        return p;
    }
}

