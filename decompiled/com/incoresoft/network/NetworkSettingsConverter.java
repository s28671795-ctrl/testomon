/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.network;

import com.incoresoft.generated.tables.records.NetworkSettingsRecord;
import com.incoresoft.network.NetworkSettings;
import java.net.URI;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class NetworkSettingsConverter
implements Converter<NetworkSettingsRecord, NetworkSettings> {
    @Override
    public NetworkSettings from(NetworkSettingsRecord databaseObject) {
        return new NetworkSettings(databaseObject.getId(), databaseObject.getName(), databaseObject.getLocalMask(), Optional.ofNullable(databaseObject.getPublicAddress()).map(URI::create).orElse(null));
    }

    @Override
    public NetworkSettingsRecord to(NetworkSettings userObject) {
        return new NetworkSettingsRecord(userObject.id(), userObject.name(), userObject.localMask(), Optional.ofNullable(userObject.publicAddress()).map(URI::toString).orElse(null));
    }

    @Override
    @NotNull
    public Class<NetworkSettingsRecord> fromType() {
        return NetworkSettingsRecord.class;
    }

    @Override
    @NotNull
    public Class<NetworkSettings> toType() {
        return NetworkSettings.class;
    }
}

