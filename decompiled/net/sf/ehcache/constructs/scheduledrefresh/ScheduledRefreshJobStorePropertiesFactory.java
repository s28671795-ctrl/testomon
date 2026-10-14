/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;

public interface ScheduledRefreshJobStorePropertiesFactory {
    public Properties jobStoreProperties(Ehcache var1, ScheduledRefreshConfiguration var2);
}

