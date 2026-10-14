/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.mapplans;

import com.incoresoft.middleware.vms.mapplans.MapPlanEventEmitter;
import com.incoresoft.middleware.vms.mapplans.MapPlanEventFactory;
import com.incoresoft.middleware.vms.mapplans.MapPlanEventFilter;
import java.util.Collection;
import java.util.UUID;

public interface MapPlanEventManager {
    public void register(MapPlanEventEmitter var1);

    public void unregister(MapPlanEventEmitter var1);

    public void processEvent(UUID var1, MapPlanEventFilter var2, MapPlanEventFactory var3);

    public void deleteEventSources(Collection<UUID> var1);
}

