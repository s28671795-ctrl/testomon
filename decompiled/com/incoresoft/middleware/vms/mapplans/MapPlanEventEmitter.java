/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.mapplans;

import com.incoresoft.middleware.model.folders.Folder;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.vms.mapplans.MapPlanEventSettings;
import com.incoresoft.middleware.vms.mapplans.MapPlanEventSource;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface MapPlanEventEmitter {
    public UUID getId();

    default public Optional<String> getPluginName() {
        return Optional.empty();
    }

    public String getLocaleKey();

    public String getIcon();

    public Collection<? extends Folder> getGroups();

    public Collection<? extends MapPlanEventSource> getSources();

    @Deprecated(forRemoval=true)
    public Class<? extends MapPlanEventSettings> getEventSettingsClass();

    default public boolean isAllowed(User user) {
        return true;
    }
}

