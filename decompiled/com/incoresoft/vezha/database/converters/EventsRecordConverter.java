/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.alibaba.fastjson.JSON;
import com.incoresoft.vezha.dto.eventmanager.NodeDTO;
import com.incoresoft.vezha.eventmanager.EventScheme;
import com.incoresoft.vezha.eventmanager.node.Node;
import com.incoresoft.vezha.generated.tables.records.EventManagerRecord;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsRecordConverter
implements Converter<EventManagerRecord, EventScheme> {
    private static final Logger log = LoggerFactory.getLogger(EventsRecordConverter.class);

    @Override
    public EventScheme from(EventManagerRecord record) {
        EventScheme item = new EventScheme();
        item.setId(record.getId());
        item.setTitle(record.getTitle());
        item.setDescription(record.getDescription());
        item.setCreatedAt(record.getCreatedAt());
        item.setUuid(record.getUuid());
        item.setNodes(JSON.parseArray(record.getNodes(), NodeDTO.class).stream().map(Node::new).toList());
        item.setClientId(record.getClientId());
        return item;
    }

    @Override
    public EventManagerRecord to(EventScheme item) {
        EventManagerRecord record = new EventManagerRecord();
        record.setId(item.getId());
        record.setUuid(item.getUuid());
        record.setTitle(item.getTitle());
        record.setDescription(item.getDescription());
        record.setCreatedAt(item.getCreatedAt());
        Optional.of(item.getNodes().stream().map(NodeDTO::new).toList()).map(JSON::toJSONString).ifPresent(record::setNodes);
        record.setClientId(item.getClientId());
        return record;
    }

    @Override
    @NotNull
    public Class<EventManagerRecord> fromType() {
        return EventManagerRecord.class;
    }

    @Override
    @NotNull
    public Class<EventScheme> toType() {
        return EventScheme.class;
    }
}

