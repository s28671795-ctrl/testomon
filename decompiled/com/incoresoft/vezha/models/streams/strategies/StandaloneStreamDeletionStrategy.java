/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.strategies;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.vezha.database.StreamsRepository;
import com.incoresoft.vezha.models.streams.exceptions.StreamHasAnalyticsException;
import com.incoresoft.vezha.models.streams.strategies.StreamDeletionStrategy;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandaloneStreamDeletionStrategy
implements StreamDeletionStrategy {
    private static final Logger log = LoggerFactory.getLogger(StandaloneStreamDeletionStrategy.class);
    private final StreamsRepository streamsRepository;
    private final AnalyticsManager analyticsManager;
    private final Storage storage;

    @Override
    public void deleteStreams(Set<Integer> streamsIds) {
        this.deleteStreams(this.streamsRepository.getAll(streamsIds));
    }

    @Override
    public void deleteStreamsByUuids(Set<UUID> streamUuids) {
        this.deleteStreams(this.streamsRepository.getAllByUuids(streamUuids));
    }

    private void deleteStreams(List<Stream> streams) {
        Set<UUID> streamUuids = streams.stream().map(Stream::getUUID).collect(Collectors.toSet());
        Set<Integer> analyticsUsingStreams = this.analyticsManager.getAll(it -> streamUuids.contains(it.getStreamUUID())).stream().map(Analytics::getId).collect(Collectors.toUnmodifiableSet());
        if (!analyticsUsingStreams.isEmpty()) {
            throw new StreamHasAnalyticsException(analyticsUsingStreams);
        }
        for (Stream stream : streams) {
            if (stream.getType() != StreamType.file) continue;
            this.storage.delete(stream.getUri().toString());
        }
        this.streamsRepository.deleteAllByUuids(streamUuids);
    }

    @Inject
    public StandaloneStreamDeletionStrategy(StreamsRepository streamsRepository, AnalyticsManager analyticsManager, Storage storage) {
        this.streamsRepository = streamsRepository;
        this.analyticsManager = analyticsManager;
        this.storage = storage;
    }
}

