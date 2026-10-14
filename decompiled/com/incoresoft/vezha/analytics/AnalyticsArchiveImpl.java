/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsArchive;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.vezha.analytics.AnalyticsManagerImpl;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;

public class AnalyticsArchiveImpl
implements AnalyticsArchive {
    private final AnalyticsManagerImpl analyticsManager;
    private final StreamsManager streamsManager;

    private Collection<Analytics> getAnalytics() {
        return this.analyticsManager.getArchivedAnalyticsMap().values();
    }

    @NotNull
    private Map<Integer, Analytics> getAnalyticsVap() {
        return this.analyticsManager.getArchivedAnalyticsMap();
    }

    @Override
    public Collection<Analytics> getAll(Predicate<Analytics> filter2) {
        return this.getAnalytics().stream().filter(it -> filter2 == null || filter2.test((Analytics)it)).toList();
    }

    @Override
    public Collection<Analytics> getAll(User user) {
        if (!user.hasPermission(CorePermissions.ViewStreams)) {
            return Collections.emptyList();
        }
        if (user.isRoot()) {
            return this.getAnalytics();
        }
        Set streamIds = this.streamsManager.getAll(user).stream().map(Stream::getId).collect(Collectors.toSet());
        return this.getAll((Analytics it) -> streamIds.contains(it.getStreamId()));
    }

    @Override
    public Analytics get(Integer id) {
        return this.getAnalytics().stream().filter(it -> it.getId() == id.intValue()).findFirst().orElse(null);
    }

    @Override
    public Collection<? extends Analytics> getAll() {
        return this.getAll((Predicate<Analytics>)null);
    }

    @Override
    public Analytics add(Analytics analytics) {
        throw new UnsupportedOperationException("Cannot explicitly add analytics to archive");
    }

    @Override
    public Analytics update(Analytics analytics) {
        throw new UnsupportedOperationException("Cannot update analytics in archive");
    }

    @Override
    public Analytics delete(Integer l) {
        throw new UnsupportedOperationException("Cannot explicitly remove analytics from archive");
    }

    @Override
    public Analytics delete(Analytics analytics) {
        throw new UnsupportedOperationException("Cannot explicitly remove analytics from archive");
    }

    public AnalyticsArchiveImpl(AnalyticsManagerImpl analyticsManager, StreamsManager streamsManager) {
        this.analyticsManager = analyticsManager;
        this.streamsManager = streamsManager;
    }
}

