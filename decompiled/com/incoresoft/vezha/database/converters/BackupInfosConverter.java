/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.plugin.commons.database.CRUDConverter;
import com.incoresoft.vezha.backup.BackupInfo;
import com.incoresoft.vezha.backup.BackupType;
import com.incoresoft.vezha.generated.tables.records.BackupsRecord;
import org.jetbrains.annotations.NotNull;

public class BackupInfosConverter
implements CRUDConverter<BackupsRecord, BackupInfo> {
    @Override
    public BackupInfo from(BackupsRecord record) {
        return new BackupInfo(record.getId(), record.getFileName(), record.getDescription(), Version.valueOf(record.getCoreVersion()), BackupType.valueOf(record.getType()), record.getCreatedAt(), record.getSize(), record.getIsVmsIntegrationEnabled(), record.getClientId());
    }

    @Override
    public BackupsRecord to(BackupInfo model) {
        return new BackupsRecord(model.getId(), model.getFileName(), model.getDescription(), model.getCoreVersion().toString(), model.getType().toString(), model.getCreatedAt(), model.getSize(), model.getClientId(), model.isVmsIntegrationEnabled());
    }

    @Override
    @NotNull
    public Class<BackupsRecord> fromType() {
        return BackupsRecord.class;
    }

    @Override
    @NotNull
    public Class<BackupInfo> toType() {
        return BackupInfo.class;
    }
}

