/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database;

import com.google.inject.Inject;
import com.incoresoft.clients.Client;
import com.incoresoft.database.converters.ClientsRecordConverter;
import com.incoresoft.generated.Tables;
import com.incoresoft.generated.tables.records.ClientsRecord;
import java.util.List;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Table;

public class ClientsRepository {
    private final DSLContext context;
    private final ClientsRecordConverter converter;

    public List<Client> getAll() {
        return this.context.selectFrom(Tables.CLIENTS).fetch().map(this.converter::from);
    }

    public boolean exists(int id) {
        return this.context.fetchExists((Table<?>)Tables.CLIENTS, Tables.CLIENTS.ID.eq(id));
    }

    public Optional<Client> get(int id) {
        return this.context.selectFrom(Tables.CLIENTS).where(Tables.CLIENTS.ID.eq(id)).fetchOptional().map(this.converter::from);
    }

    public Client add(Client client) {
        ClientsRecord record = this.context.newRecord(Tables.CLIENTS, this.converter.to(client));
        record.reset(Tables.CLIENTS.ID);
        record.insert();
        return this.converter.from(record);
    }

    public Client update(Client client) {
        ClientsRecord record = this.context.newRecord(Tables.CLIENTS, this.converter.to(client));
        record.update();
        record.changed(Tables.CLIENTS.ID, false);
        return this.converter.from(record);
    }

    public void delete(int id) {
        this.context.deleteFrom(Tables.CLIENTS).where(Tables.CLIENTS.ID.eq(id)).execute();
    }

    @Inject
    public ClientsRepository(DSLContext context, ClientsRecordConverter converter) {
        this.context = context;
        this.converter = converter;
    }
}

