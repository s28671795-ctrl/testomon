/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.BackupDatabaseUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jooq.Table;

public class BackupIgnoredTables {
    private final Set<String> formattedNames = new HashSet<String>();

    public BackupIgnoredTables(Collection<String> tableNames) {
        for (String tableName : tableNames) {
            this.formattedNames.add(BackupDatabaseUtils.formatName(tableName));
        }
    }

    public void add(String tableName) {
        this.formattedNames.add(BackupDatabaseUtils.formatName(tableName));
    }

    public void add(Table<?> table) {
        this.formattedNames.add(BackupDatabaseUtils.formatName(table.getName()));
    }

    public boolean contains(String tableName) {
        return this.formattedNames.contains(BackupDatabaseUtils.formatName(tableName));
    }

    public boolean contains(Table<?> table) {
        return this.formattedNames.contains(BackupDatabaseUtils.formatName(table.getName()));
    }

    public boolean isEmpty() {
        return this.formattedNames.isEmpty();
    }

    public BackupIgnoredTables() {
    }
}

