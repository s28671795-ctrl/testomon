/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.google.inject.Inject;
import com.incoresoft.vezha.generated.tables.records.ServersRecord;
import com.incoresoft.vezha.models.servers.ServerPersistenceInfo;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServersRecordConverter
implements Converter<ServersRecord, ServerPersistenceInfo> {
    private static final Logger log = LoggerFactory.getLogger(ServersRecordConverter.class);

    @Override
    public ServerPersistenceInfo from(ServersRecord record) {
        return new ServerPersistenceInfo(record.getId(), record.getName(), record.getIsExternalAddressEnabled(), record.getAddress(), record.getPort());
    }

    @Override
    public ServersRecord to(ServerPersistenceInfo item) {
        return new ServersRecord(item.id(), item.name(), item.isExternalAddressEnabled(), item.externalAddress(), item.externalPort());
    }

    @Override
    @NotNull
    public Class<ServersRecord> fromType() {
        return ServersRecord.class;
    }

    @Override
    @NotNull
    public Class<ServerPersistenceInfo> toType() {
        return ServerPersistenceInfo.class;
    }

    @Inject
    public ServersRecordConverter() {
    }
}

