/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.mapplans;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.vms.mapplans.MapPlanEventSourceParameters;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public interface MapPlanEventSource {
    public UUID getId();

    public String getName();

    public int getParentId();

    public boolean isAllowed(User var1);

    default public Collection<? extends MapPlanEventSource> getSubSources() {
        return Collections.emptyList();
    }

    public MapPlanEventSourceParameters getParameters();
}

