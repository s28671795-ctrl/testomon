/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.qdrant;

import com.incoresoft.plugin.commons.qdrant.QDrantClient;
import com.incoresoft.plugin.commons.qdrant.dto.filter.FilterDTO;
import com.incoresoft.plugin.commons.qdrant.dto.vectors.GetVectorsResponseResultItemDTO;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class QDrantCleaningTask
implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(QDrantCleaningTask.class);
    private static final int PASS_LIMIT = 1000;
    private final QDrantClient collectionClient;
    private final Duration retentionPeriod;
    private final String timestampFieldName;

    protected QDrantCleaningTask(@NotNull QDrantClient collectionClient, @NotNull Duration retentionPeriod, @NotNull String timestampFieldName) {
        this.collectionClient = Objects.requireNonNull(collectionClient);
        this.retentionPeriod = Objects.requireNonNull(retentionPeriod);
        this.timestampFieldName = Objects.requireNonNull(timestampFieldName);
    }

    @Override
    public void run() {
        List<Long> ids;
        long earliestTimestampToKeep = Instant.now().minus(this.retentionPeriod).toEpochMilli();
        List<FilterDTO> filterDTOS = List.of(FilterDTO.rangeExcluding(this.timestampFieldName, 0L, earliestTimestampToKeep));
        int deletedRecords = 0;
        while (!(ids = this.collectionClient.scrollVectors(filterDTOS, 1000, 0).getResult().getPoints().stream().map(GetVectorsResponseResultItemDTO::getId).collect(Collectors.toList())).isEmpty()) {
            this.collectionClient.delete(ids);
            deletedRecords += ids.size();
        }
        log.info("Deleted {} vectors from collection \"{}\"", (Object)deletedRecords, (Object)this.collectionClient.getCollectionName());
    }
}

