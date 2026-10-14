/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.strategies;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.vezha.database.StreamsRepository;
import com.incoresoft.vezha.models.streams.strategies.StreamDeletionStrategy;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegratedStreamDeletionStrategy
implements StreamDeletionStrategy {
    private static final Logger log = LoggerFactory.getLogger(IntegratedStreamDeletionStrategy.class);
    private final StreamsRepository streamsRepository;
    private final AnalyticsManager analyticsManager;

    @Override
    public void deleteStreams(Set<Integer> streamsIds) {
        List<Stream> streams = this.streamsRepository.getAll(streamsIds);
        Set<UUID> streamUuids = streams.stream().map(Stream::getUUID).collect(Collectors.toSet());
        this.deleteStreamsByUuids(streamUuids);
    }

    @Override
    public void deleteStreamsByUuids(Set<UUID> streamUuids) {
        Collection<Analytics> analytics = this.analyticsManager.getAll(it -> streamUuids.contains(it.getStreamUUID()));
        Set<Integer> analyticsToStop = analytics.stream().filter(it -> it.getStatus() != AnalyticsStatus.stopped).map(Analytics::getId).collect(Collectors.toSet());
        this.analyticsManager.stopAll(analyticsToStop);
        analytics.forEach(this.analyticsManager::delete);
        this.streamsRepository.deleteAllByUuids(streamUuids);
    }

    @Inject
    public IntegratedStreamDeletionStrategy(StreamsRepository streamsRepository, AnalyticsManager analyticsManager) {
        this.streamsRepository = streamsRepository;
        this.analyticsManager = analyticsManager;
    }
}

