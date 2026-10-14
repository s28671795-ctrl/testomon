/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.cache;

import java.net.URL;
import org.apache.cxf.Bus;
import org.apache.cxf.buslifecycle.BusLifeCycleListener;
import org.apache.cxf.buslifecycle.BusLifeCycleManager;
import org.apache.cxf.ws.security.cache.EHCacheUtils;
import org.apache.wss4j.common.cache.EHCacheReplayCache;

public class CXFEHCacheReplayCache
extends EHCacheReplayCache
implements BusLifeCycleListener {
    private final Bus bus;

    public CXFEHCacheReplayCache(String key, Bus bus, URL configFileURL) {
        super(key, EHCacheUtils.getCacheManager(bus, configFileURL));
        this.bus = bus;
        if (bus != null) {
            bus.getExtension(BusLifeCycleManager.class).registerLifeCycleListener(this);
        }
    }

    @Override
    public void close() {
        super.close();
        if (this.bus != null) {
            this.bus.getExtension(BusLifeCycleManager.class).unregisterLifeCycleListener(this);
        }
    }
}

