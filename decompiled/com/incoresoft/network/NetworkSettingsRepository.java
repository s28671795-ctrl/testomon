/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.network;

import com.incoresoft.generated.Tables;
import com.incoresoft.generated.tables.records.NetworkSettingsRecord;
import com.incoresoft.network.NetworkSettings;
import com.incoresoft.network.NetworkSettingsConverter;
import jakarta.inject.Inject;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;

public class NetworkSettingsRepository {
    private final DSLContext context;
    private final NetworkSettingsConverter converter;

    @Inject
    public NetworkSettingsRepository(@NotNull DSLContext dslContext, @NotNull NetworkSettingsConverter converter) {
        this.context = Objects.requireNonNull(dslContext);
        this.converter = Objects.requireNonNull(converter);
    }

    public NetworkSettings get() {
        return this.context.selectFrom(Tables.NETWORK_SETTINGS).fetchOne(this.converter::from);
    }

    public NetworkSettings update(NetworkSettings settings) {
        NetworkSettingsRecord databaseRecord = this.context.newRecord(Tables.NETWORK_SETTINGS, this.converter.to(settings));
        databaseRecord.changed(Tables.NETWORK_SETTINGS.ID, false);
        databaseRecord.update();
        return this.converter.from(databaseRecord);
    }
}

