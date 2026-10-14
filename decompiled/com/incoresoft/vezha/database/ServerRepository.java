/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.vezha.database.converters.ServersRecordConverter;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.ServersRecord;
import com.incoresoft.vezha.models.servers.ServerPersistenceInfo;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.jooq.DSLContext;

public class ServerRepository {
    private final DSLContext create;
    private final ServersRecordConverter converter;
    private final ConcurrentHashMap<String, ServerPersistenceInfo> cache = new ConcurrentHashMap();

    public ServerPersistenceInfo getServerInfo(String id) {
        Objects.requireNonNull(id, "id");
        return this.cache.computeIfAbsent(id, key -> this.create.selectFrom(Tables.SERVERS).where(Tables.SERVERS.ID.eq((String)key)).fetchOptional().map(this.converter::from).orElseGet(() -> new ServerPersistenceInfo((String)key, "", false, "", 0)));
    }

    public void upsert(ServerPersistenceInfo server) {
        Objects.requireNonNull(server, "server");
        ServersRecord r = this.converter.to(server);
        this.create.insertInto(Tables.SERVERS).set(r).onDuplicateKeyUpdate().set(Tables.SERVERS.NAME, r.getName()).set(Tables.SERVERS.IS_EXTERNAL_ADDRESS_ENABLED, r.getIsExternalAddressEnabled()).set(Tables.SERVERS.ADDRESS, r.getAddress()).set(Tables.SERVERS.PORT, r.getPort()).execute();
        this.cache.put(server.id(), server);
    }

    @Inject
    public ServerRepository(DSLContext create, ServersRecordConverter converter) {
        this.create = create;
        this.converter = converter;
    }
}

