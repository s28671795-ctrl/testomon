/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.constructs.scheduledrefresh;

import java.util.Properties;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshCacheExtension;
import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshConfiguration;
import net.sf.ehcache.extension.CacheExtension;
import net.sf.ehcache.extension.CacheExtensionFactory;

public class ScheduledRefreshCacheExtensionFactory
extends CacheExtensionFactory {
    @Override
    public CacheExtension createCacheExtension(Ehcache cache, Properties properties) {
        ScheduledRefreshConfiguration config = new ScheduledRefreshConfiguration().fromProperties(properties).build();
        return new ScheduledRefreshCacheExtension(config, cache);
    }
}

