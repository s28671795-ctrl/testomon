/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.HashMap;
import java.util.Map;
import liquibase.change.DatabaseChangeProperty;
import liquibase.statement.core.InsertStatement;

public class InsertOrUpdateStatement
extends InsertStatement {
    private final String primaryKey;
    private Boolean onlyUpdate = Boolean.FALSE;
    private final Map<String, Boolean> allowUpdates = new HashMap<String, Boolean>();

    public InsertOrUpdateStatement(String catalogName, String schemaName, String tableName, String primaryKey) {
        super(catalogName, schemaName, tableName);
        this.primaryKey = primaryKey;
    }

    public InsertOrUpdateStatement(String catalogName, String schemaName, String tableName, String primaryKey, boolean onlyUpdate) {
        this(catalogName, schemaName, tableName, primaryKey);
        this.onlyUpdate = onlyUpdate;
    }

    public String getPrimaryKey() {
        return this.primaryKey;
    }

    @DatabaseChangeProperty(description="Whether records with no matching database record should be ignored")
    public Boolean getOnlyUpdate() {
        if (this.onlyUpdate == null) {
            return false;
        }
        return this.onlyUpdate;
    }

    public void setOnlyUpdate(Boolean onlyUpdate) {
        this.onlyUpdate = onlyUpdate == null ? Boolean.FALSE : onlyUpdate;
    }

    public boolean getAllowColumnUpdate(String columnName) {
        Boolean allow = this.allowUpdates.get(columnName);
        if (allow == null) {
            return true;
        }
        return allow;
    }

    public void setAllowColumnUpdate(String columnName, boolean allowUpdate) {
        this.allowUpdates.put(columnName, allowUpdate);
    }
}

