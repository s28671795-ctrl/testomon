/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant;

import com.incoresoft.plugin.commons.qdrant.NativeQdrantClient;
import io.qdrant.client.ConditionFactory;
import io.qdrant.client.PointIdFactory;
import io.qdrant.client.grpc.Points;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NativeQdrantCleaningTask
implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(NativeQdrantCleaningTask.class);
    private static final int DEFAULT_CLEANING_BATCH_SIZE = 1000;
    private final NativeQdrantClient collectionClient;
    private final Duration retentionPeriod;
    private final String timestampFieldName;

    protected NativeQdrantCleaningTask(@NotNull NativeQdrantClient collectionClient, @NotNull Duration retentionPeriod, @NotNull String timestampFieldName) {
        this.collectionClient = Objects.requireNonNull(collectionClient);
        this.retentionPeriod = Objects.requireNonNull(retentionPeriod);
        this.timestampFieldName = Objects.requireNonNull(timestampFieldName);
    }

    @Override
    public void run() {
        List<Long> ids;
        long earliestTimestampToKeep = Instant.now().minus(this.retentionPeriod).toEpochMilli();
        Points.Filter filter2 = Points.Filter.newBuilder().addMust(ConditionFactory.range(this.timestampFieldName, Points.Range.newBuilder().setGt(0.0).setLt(earliestTimestampToKeep).build())).build();
        int deletedRecords = 0;
        while (!(ids = this.collectionClient.get(filter2, 1000, PointIdFactory.id(0L), false, false).getResultList().stream().map(it -> it.getId().getNum()).collect(Collectors.toList())).isEmpty()) {
            this.collectionClient.delete(ids);
            deletedRecords += ids.size();
        }
        log.info("Deleted {} vectors from collection \"{}\"", (Object)deletedRecords, (Object)this.collectionClient.getCollectionName());
    }
}

