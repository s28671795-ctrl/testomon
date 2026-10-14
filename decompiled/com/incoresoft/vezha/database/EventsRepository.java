/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.vezha.database.converters.EventsRecordConverter;
import com.incoresoft.vezha.eventmanager.EventScheme;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.EventManagerRecord;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.jooq.DSLContext;

public class EventsRepository {
    private final DSLContext create;
    private final EventsRecordConverter converter;

    public List<EventScheme> getItems() {
        return this.create.selectFrom(Tables.EVENT_MANAGER).fetch().map(this.converter::from).stream().filter(Objects::nonNull).toList();
    }

    public EventScheme add(EventScheme item) {
        item.setUuid(UUID.randomUUID().toString());
        EventManagerRecord record = this.create.newRecord(Tables.EVENT_MANAGER);
        record.from((Object)this.converter.to(item));
        record.reset(Tables.EVENT_MANAGER.ID);
        record.insert();
        return this.converter.from(record);
    }

    public EventScheme update(EventScheme item) {
        EventManagerRecord record = this.create.newRecord(Tables.EVENT_MANAGER);
        record.from((Object)this.converter.to(item));
        record.changed(Tables.EVENT_MANAGER.ID, false);
        record.update();
        return this.converter.from(record);
    }

    public void delete(Integer id) {
        EventManagerRecord record = this.create.newRecord(Tables.EVENT_MANAGER);
        record.setId(id);
        record.delete();
    }

    @Inject
    public EventsRepository(DSLContext create, EventsRecordConverter converter) {
        this.create = create;
        this.converter = converter;
    }
}

