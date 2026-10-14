/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsArchive;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadata;
import com.incoresoft.middleware.analytics.metadata.AnalyticsMetadataListener;
import com.incoresoft.middleware.analytics.service.ServiceMetadata;
import com.incoresoft.middleware.analytics.service.ServiceMetadataListener;
import com.incoresoft.middleware.analytics.state.AnalyticsStateListener;
import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.model.user.User;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;

public interface AnalyticsManager
extends ItemManager<Analytics> {
    public Collection<Analytics> getAll(Predicate<Analytics> var1);

    public Analytics get(UUID var1);

    public Collection<Analytics> getAll(User var1);

    default public Collection<Analytics> getAll(boolean showDeleted) {
        return this.getAll((Predicate<Analytics>)null);
    }

    public void updateSignature(Analytics var1);

    @Override
    default public Analytics delete(Analytics analytics) {
        return (Analytics)((Object)this.delete(analytics.getId()));
    }

    public void start(int var1);

    public void stop(int var1);

    public void pause(int var1);

    public void startAll(Collection<Integer> var1);

    public void stopAll(Collection<Integer> var1);

    public void addAnalyticsStateListener(AnalyticsStateListener var1);

    public void removeAnalyticsStateListener(AnalyticsStateListener var1);

    public <T extends AnalyticsMetadata> void addAnalyticsMetadataListener(AnalyticsMetadataListener<T> var1);

    public <T extends AnalyticsMetadata> void removeAnalyticsMetadataListener(AnalyticsMetadataListener<T> var1);

    public <T extends ServiceMetadata> void addAnalyticsServiceListener(ServiceMetadataListener<T> var1);

    public <T extends ServiceMetadata> void removeAnalyticsServiceListener(ServiceMetadataListener<T> var1);

    public AnalyticsArchive getAnalyticsArchive();
}

