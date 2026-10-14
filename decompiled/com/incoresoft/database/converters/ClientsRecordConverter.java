/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.converters;

import com.incoresoft.clients.Client;
import com.incoresoft.generated.tables.records.ClientsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientsRecordConverter
implements Converter<ClientsRecord, Client> {
    private static final Logger log = LoggerFactory.getLogger(ClientsRecordConverter.class);

    @Override
    public Client from(ClientsRecord record) {
        Client client = new Client();
        client.setId(record.getId());
        client.setName(record.getClientName());
        client.setCountry(record.getCountry());
        client.setCity(record.getCity());
        client.setAddress(record.getAddress());
        client.setZipCode(record.getZipCode());
        client.setEmail(record.getEmail());
        client.setPhone(record.getPhone());
        client.setComment(record.getComment());
        return client;
    }

    @Override
    public ClientsRecord to(Client client) {
        ClientsRecord record = new ClientsRecord();
        record.setId(client.getId());
        record.setClientName(client.getName());
        record.setCountry(client.getCountry());
        record.setCity(client.getCity());
        record.setAddress(client.getAddress());
        record.setZipCode(client.getZipCode());
        record.setEmail(client.getEmail());
        record.setPhone(client.getPhone());
        record.setComment(client.getComment());
        return record;
    }

    @Override
    @NotNull
    public Class<ClientsRecord> fromType() {
        return ClientsRecord.class;
    }

    @Override
    @NotNull
    public Class<Client> toType() {
        return Client.class;
    }
}

