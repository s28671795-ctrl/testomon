/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics;

import com.incoresoft.middleware.analytics.AnalyticsError;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.model.analytics.AnalyticsHardwareInfo;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import io.javalin.http.Context;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface Analytics
extends Model {
    @Override
    public int getId();

    public UUID getUUID();

    public String getPluginName();

    public String getName();

    public String getH264Preview();

    default public String getH264Preview(Context ctx) {
        return this.getH264Preview();
    }

    public String getMJPEGPreview();

    default public String getMJPEGPreview(Context ctx) {
        return this.getMJPEGPreview();
    }

    public AnalyticsModule getModule();

    @Deprecated(forRemoval=true)
    public int getStreamId();

    public UUID getStreamUUID();

    public Instant getCreatedAt();

    public Instant getStartInstant();

    public AnalyticsStatus getStatus();

    default public Set<String> getAllowedServerIds() {
        return null;
    }

    public RoleUserRestrictions getRestrictions();

    public AnalyticsHardwareInfo getHardwareInfo();

    public EventsHolder getEventsHolder();

    default public boolean isH264Stream() {
        return false;
    }

    default public boolean hasErrors() {
        Collection<? extends AnalyticsError> errors2 = this.getErrors();
        return errors2 == null || !errors2.isEmpty();
    }

    public Collection<? extends AnalyticsError> getErrors();

    default public int getParentId() {
        return 0;
    }
}

