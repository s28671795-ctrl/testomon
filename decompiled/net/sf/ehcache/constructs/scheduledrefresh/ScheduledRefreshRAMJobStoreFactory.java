/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.simpl.RAMJobStore
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshJobStorePropertiesFactory;
import org.quartz.simpl.RAMJobStore;

public class ScheduledRefreshRAMJobStoreFactory
implements ScheduledRefreshJobStorePropertiesFactory {
    @Override
    public Properties jobStoreProperties(Ehcache underlyingCache, ScheduledRefreshConfiguration config) {
        Properties p = new Properties();
        p.put("org.quartz.jobStore.class", RAMJobStore.class.getName());
        return p;
    }
}

