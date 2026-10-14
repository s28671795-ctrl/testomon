/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.google.inject.Inject;
import com.incoresoft.backup.database.BackupDatabaseUtils;
import com.incoresoft.config.ConfigManager;
import com.incoresoft.database.settings.DatabaseUrlComponents;
import com.incoresoft.database.settings.DatabaseUrlParsingManager;
import com.incoresoft.database.settings.exceptions.DatabaseTypeNotSupportedException;
import com.incoresoft.middleware.database.DBType;
import java.util.List;
import java.util.function.Predicate;
import org.jooq.DSLContext;
import org.jooq.Schema;

public class JooqSchemaManager {
    private final DatabaseUrlParsingManager databaseUrlParsingManager;
    private final ConfigManager<?> configManager;

    public Schema getMainSchema(DSLContext context) {
        DatabaseUrlComponents components = this.databaseUrlParsingManager.parseUrl(this.configManager.getDatabase().getUrl());
        String databaseName = components.getDatabaseName();
        String schemaName = this.configManager.getDatabase().getSchemaName();
        Predicate<Schema> schemaFilter = switch (components.getType()) {
            case DBType.mysql -> schema -> schema.getName().equals(databaseName);
            case DBType.postgresql -> schema -> schema.getName().equals(schemaName);
            case DBType.sqlserver -> schema -> schema.getCatalog() != null && schema.getCatalog().getName().equals(databaseName) && schema.getName().equals("dbo");
            case DBType.h2 -> schema -> schema.getName().equals("PUBLIC");
            default -> throw new DatabaseTypeNotSupportedException(components.getType());
        };
        List<Schema> sourceSchemas = context.meta().getSchemas();
        return sourceSchemas.stream().filter(schemaFilter).findFirst().orElseThrow(() -> new RuntimeException("Schema not found: " + schemaName));
    }

    public Schema getBackupSchema(DSLContext backupContext) {
        String schemaName = BackupDatabaseUtils.formatName("vms");
        return backupContext.meta().getSchemas().stream().filter(schema -> schema.getName().equals(schemaName)).findFirst().orElseThrow(() -> new RuntimeException("Schema not found: " + schemaName));
    }

    @Inject
    public JooqSchemaManager(DatabaseUrlParsingManager databaseUrlParsingManager, ConfigManager<?> configManager) {
        this.databaseUrlParsingManager = databaseUrlParsingManager;
        this.configManager = configManager;
    }
}

