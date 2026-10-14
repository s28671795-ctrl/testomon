/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.Utils.Pair;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.streams.params.EmptyParams;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.sorting.SortFields;
import com.incoresoft.plugin.commons.sorting.SortOrders;
import com.incoresoft.plugin.commons.sorting.SortParams;
import com.incoresoft.vezha.database.converters.StreamGroupRecordConverter;
import com.incoresoft.vezha.database.converters.StreamsRecordConverter;
import com.incoresoft.vezha.dto.streams.StreamSearchParams;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.StreamGroups;
import com.incoresoft.vezha.generated.tables.Streams;
import com.incoresoft.vezha.generated.tables.records.StreamGroupsRecord;
import com.incoresoft.vezha.generated.tables.records.StreamsRecord;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.TableField;
import org.jooq.impl.DSL;

public class StreamsRepository {
    private final DSLContext create;
    private final StreamsRecordConverter converter;
    private final StreamGroupRecordConverter streamGroupRecordConverter;

    public Optional<Stream> get(Integer id) {
        return this.create.selectFrom(Streams.STREAMS).where(Streams.STREAMS.ID.eq(id).and(Streams.STREAMS.STATUS.notEqual((byte)StreamStatus.deleted.getId()))).fetchOptional(this.converter::from);
    }

    public List<Stream> getAll(Set<Integer> ids) {
        return this.getAll(ids, this.create);
    }

    public List<Stream> getAll(Set<Integer> ids, DSLContext transaction) {
        return transaction.selectFrom(Streams.STREAMS).where(Streams.STREAMS.ID.in((Collection<?>)ids)).and(Streams.STREAMS.STATUS.notEqual((byte)StreamStatus.deleted.getId())).fetch(this.converter::from);
    }

    public List<Stream> getAllByUuids(Set<UUID> uuids) {
        return this.create.selectFrom(Streams.STREAMS).where(Streams.STREAMS.UUID.in((Collection<?>)uuids)).and(Streams.STREAMS.STATUS.notEqual((byte)StreamStatus.deleted.getId())).fetch(this.converter::from);
    }

    public List<Stream> getAll(boolean showDeleted) {
        Condition condition = showDeleted ? DSL.noCondition() : Streams.STREAMS.STATUS.notEqual((byte)StreamStatus.deleted.getId());
        return this.create.selectFrom(Streams.STREAMS).where(condition).orderBy(Streams.STREAMS.CREATED_AT.desc()).fetch(this.converter::from);
    }

    public Pair<Long, List<StreamImpl>> getAll(User user, StreamSearchParams searchParams, PaginationParams paginationParams, SortParams sortParams) {
        ArrayList<Condition> conditions = new ArrayList<Condition>();
        if (!user.isRoot()) {
            conditions.add(Streams.STREAMS.CLIENT_ID.eq(user.getClientId()));
        }
        if (searchParams.name() != null && !searchParams.name().isBlank()) {
            conditions.add(Streams.STREAMS.NAME.containsIgnoreCase(searchParams.name()));
        }
        if (searchParams.type() != null && !searchParams.type().isBlank()) {
            conditions.add(Streams.STREAMS.TYPE.equalIgnoreCase(searchParams.type()));
        }
        if (!searchParams.showDeleted()) {
            conditions.add(Streams.STREAMS.STATUS.ne((byte)StreamStatus.deleted.getId()));
        }
        if (searchParams.uuids() != null && !searchParams.uuids().isEmpty()) {
            conditions.add(Streams.STREAMS.UUID.in((Collection<?>)searchParams.uuids()));
        }
        SortFields sortFields = sortParams.getSortField();
        int n = 0;
        TableField<StreamsRecord, Object> sortField = switch (SwitchBootstraps.enumSwitch("enumSwitch", new Object[]{"ID", "NAME"}, (SortFields)sortFields, n)) {
            case 0 -> Streams.STREAMS.ID;
            case 1 -> Streams.STREAMS.NAME;
            default -> Streams.STREAMS.CREATED_AT;
        };
        SortField orderField = sortParams.getSortOrder() == SortOrders.ASC ? sortField.asc() : sortField.desc();
        List<StreamImpl> allMatchingStreams = this.create.selectFrom(Streams.STREAMS).where(conditions).orderBy(orderField).fetch(this.converter::from).stream().filter(it -> it.getRestrictions().isAllowed(user, CorePermissions.ViewStreams)).toList();
        List data = allMatchingStreams.stream().skip(paginationParams.getOffset()).limit(paginationParams.getLimit().orElse(Integer.MAX_VALUE).intValue()).toList();
        return new Pair<Long, List<StreamImpl>>(Long.valueOf(allMatchingStreams.size()), data);
    }

    public Optional<Stream> get(UUID streamUUID) {
        return this.create.selectFrom(Streams.STREAMS).where(Streams.STREAMS.UUID.eq(streamUUID)).and(Streams.STREAMS.STATUS.notEqual((byte)StreamStatus.deleted.getId())).fetchOptional(this.converter::from);
    }

    public StreamImpl add(Stream item) {
        StreamsRecord record = this.create.newRecord(Streams.STREAMS);
        record.from((Object)this.converter.to((StreamImpl)item));
        record.reset(Streams.STREAMS.ID);
        record.insert();
        return this.converter.from(record);
    }

    public StreamImpl update(Stream item) {
        return this.update(item, this.create);
    }

    public StreamImpl update(Stream item, DSLContext transaction) {
        StreamsRecord record = transaction.newRecord(Streams.STREAMS);
        record.from((Object)this.converter.to((StreamImpl)item));
        record.changed(Streams.STREAMS.ID, false);
        record.update();
        return this.converter.from(record);
    }

    public List<Stream> findByNames(Set<String> names) {
        return this.create.selectFrom(Streams.STREAMS).where(Streams.STREAMS.NAME.in((Collection<?>)names).and(Streams.STREAMS.STATUS.notEqual((byte)StreamStatus.deleted.getId()))).fetch(this.converter::from);
    }

    public List<StreamGroup> getStreamGroups() {
        return this.create.selectFrom(StreamGroups.STREAM_GROUPS).fetch().map(this.streamGroupRecordConverter::from).stream().filter(Objects::nonNull).toList();
    }

    public StreamGroup upsertGroup(StreamGroup group) {
        StreamGroupsRecord record = this.create.newRecord(StreamGroups.STREAM_GROUPS);
        if (group.getId() == -2) {
            record.from((Object)this.streamGroupRecordConverter.to(group));
            record.reset(StreamGroups.STREAM_GROUPS.ID);
            record.insert();
        } else {
            record.from((Object)this.streamGroupRecordConverter.to(group));
            record.changed(StreamGroups.STREAM_GROUPS.ID, false);
            record.update();
        }
        return this.streamGroupRecordConverter.from(record);
    }

    public void deleteGroup(int id) {
        StreamGroupsRecord record = this.create.newRecord(StreamGroups.STREAM_GROUPS);
        record.setId(id);
        record.delete();
    }

    public void deleteAllByUuids(Set<UUID> uuids) {
        this.create.update(Streams.STREAMS).set(Streams.STREAMS.STATUS, (byte)StreamStatus.deleted.getId()).where(Streams.STREAMS.UUID.in((Collection<?>)uuids)).execute();
    }

    public void renameAll(Map<Integer, String> renameMap, DSLContext transaction) {
        List<StreamsRecord> records = renameMap.entrySet().stream().map(it -> {
            StreamsRecord record = new StreamsRecord();
            record.setId((Integer)it.getKey());
            record.changed(Tables.ANALYTICS.ID, false);
            record.setName((String)it.getValue());
            return record;
        }).toList();
        transaction.batchUpdate(records).execute();
    }

    public void updateLocation(Set<UUID> streamUuids, CoordinatesDTO location) {
        this.create.update(Streams.STREAMS).set(Streams.STREAMS.LAT, location.getLatitude()).set(Streams.STREAMS.LNG, location.getLongitude()).where(Streams.STREAMS.UUID.in((Collection<?>)streamUuids)).execute();
    }

    public void markAsEmbedded(Stream stream) {
        this.create.update(Streams.STREAMS).set(Streams.STREAMS.TYPE, StreamType.embedded.name()).set(Streams.STREAMS.PARAMS, JSON.toJSONString(new EmptyParams(StreamType.embedded))).where(Streams.STREAMS.ID.eq(stream.getId())).execute();
    }

    public void setStatus(int id, StreamStatus status) {
        this.create.update(Streams.STREAMS).set(Streams.STREAMS.STATUS, (byte)status.getId()).where(Streams.STREAMS.ID.eq(id)).and(Streams.STREAMS.STATUS.notEqual((byte)StreamStatus.deleted.getId())).execute();
    }

    @Inject
    public StreamsRepository(DSLContext create, StreamsRecordConverter converter, StreamGroupRecordConverter streamGroupRecordConverter) {
        this.create = create;
        this.converter = converter;
        this.streamGroupRecordConverter = streamGroupRecordConverter;
    }
}

