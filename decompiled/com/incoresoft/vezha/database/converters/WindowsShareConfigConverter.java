/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import com.incoresoft.vezha.generated.tables.records.WindowsShareFolderConfigsRecord;
import com.incoresoft.vezha.windowsshare.WindowsShareConfigImpl;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;

public class WindowsShareConfigConverter
implements Converter<WindowsShareFolderConfigsRecord, WindowsShareConfig> {
    @Override
    public WindowsShareConfig from(WindowsShareFolderConfigsRecord databaseObject) {
        return new WindowsShareConfigImpl(databaseObject.getId(), databaseObject.getHost(), databaseObject.getUsername(), databaseObject.getPassword(), databaseObject.getDomain(), databaseObject.getShareName());
    }

    @Override
    public WindowsShareFolderConfigsRecord to(WindowsShareConfig userObject) {
        return new WindowsShareFolderConfigsRecord(userObject.getId(), userObject.getHostname(), userObject.getShareName(), userObject.getUsername(), userObject.getPassword(), userObject.getDomain());
    }

    @Override
    @NotNull
    public Class<WindowsShareFolderConfigsRecord> fromType() {
        return WindowsShareFolderConfigsRecord.class;
    }

    @Override
    @NotNull
    public Class<WindowsShareConfig> toType() {
        return WindowsShareConfig.class;
    }
}

