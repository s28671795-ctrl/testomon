/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.google.inject.Inject;
import com.incoresoft.vezha.generated.tables.records.StreamGroupsRecord;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import com.incoresoft.vezha.models.streams.model.StreamRestrictions;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamGroupRecordConverter
implements Converter<StreamGroupsRecord, StreamGroup> {
    private static final Logger log = LoggerFactory.getLogger(StreamGroupRecordConverter.class);
    private final StreamRestrictions.Converter streamRestrictionsConverter;

    @Override
    public StreamGroup from(StreamGroupsRecord record) {
        StreamGroup streamGroup = new StreamGroup();
        streamGroup.setId(record.getId());
        streamGroup.setParentId(record.getParentId());
        streamGroup.setName(record.getName());
        streamGroup.setClientId(record.getClientId());
        return streamGroup;
    }

    @Override
    public StreamGroupsRecord to(StreamGroup item) {
        StreamGroupsRecord record = new StreamGroupsRecord();
        record.setId(item.getId());
        record.setParentId(item.getParentId());
        record.setName(item.getName());
        record.setClientId(item.getClientId());
        return record;
    }

    @Override
    @NotNull
    public Class<StreamGroupsRecord> fromType() {
        return StreamGroupsRecord.class;
    }

    @Override
    @NotNull
    public Class<StreamGroup> toType() {
        return StreamGroup.class;
    }

    @Inject
    public StreamGroupRecordConverter(StreamRestrictions.Converter streamRestrictionsConverter) {
        this.streamRestrictionsConverter = streamRestrictionsConverter;
    }
}

