/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.middleware.windowsshare.WindowsShareConfig;
import com.incoresoft.vezha.database.converters.WindowsShareConfigConverter;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.WindowsShareFolderConfigsRecord;
import java.util.List;
import org.jooq.DSLContext;

public class WindowsShareRepository {
    private final DSLContext create;
    private final WindowsShareConfigConverter converter;

    public List<WindowsShareConfig> getAll() {
        return this.create.selectFrom(Tables.WINDOWS_SHARE_FOLDER_CONFIGS).fetch().map(this.converter::from);
    }

    public WindowsShareConfig add(WindowsShareConfig windowsShareConfig) {
        WindowsShareFolderConfigsRecord record = this.create.newRecord(Tables.WINDOWS_SHARE_FOLDER_CONFIGS);
        record.from((Object)this.converter.to(windowsShareConfig));
        record.reset(Tables.WINDOWS_SHARE_FOLDER_CONFIGS.ID);
        record.insert();
        return this.converter.from(record);
    }

    public WindowsShareConfig update(WindowsShareConfig windowsShareConfig) {
        WindowsShareFolderConfigsRecord record = this.create.newRecord(Tables.WINDOWS_SHARE_FOLDER_CONFIGS);
        record.from((Object)this.converter.to(windowsShareConfig));
        record.changed(Tables.WINDOWS_SHARE_FOLDER_CONFIGS.ID, false);
        record.update();
        return this.converter.from(record);
    }

    public void delete(int id) {
        WindowsShareFolderConfigsRecord record = this.create.newRecord(Tables.WINDOWS_SHARE_FOLDER_CONFIGS);
        record.setId(id);
        record.delete();
    }

    @Inject
    public WindowsShareRepository(DSLContext create, WindowsShareConfigConverter converter) {
        this.create = create;
        this.converter = converter;
    }
}

