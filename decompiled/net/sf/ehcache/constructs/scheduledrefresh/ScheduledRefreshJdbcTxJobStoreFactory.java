/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.impl.jdbcjobstore.JobStoreTX
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshJobStorePropertiesFactory;
import org.quartz.impl.jdbcjobstore.JobStoreTX;

public class ScheduledRefreshJdbcTxJobStoreFactory
implements ScheduledRefreshJobStorePropertiesFactory {
    @Override
    public Properties jobStoreProperties(Ehcache underlyingCache, ScheduledRefreshConfiguration config) {
        Properties p = new Properties(config.getExcessProperties());
        p.put("org.quartz.scheduler.instanceId", "AUTO");
        p.put("org.quartz.jobStore.class", JobStoreTX.class.getName());
        return p;
    }
}

