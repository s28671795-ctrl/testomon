/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.settings;

import com.incoresoft.dto.config.SetupDatabaseConfigDTO;
import com.incoresoft.middleware.database.DBType;

public final class DatabaseUrlComponents {
    private final DBType type;
    private final String host;
    private final String databaseName;

    public DatabaseUrlComponents(SetupDatabaseConfigDTO setupDTO) {
        this(setupDTO.getType(), setupDTO.getHost(), setupDTO.getDatabaseName());
    }

    public DBType getType() {
        return this.type;
    }

    public String getHost() {
        return this.host;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DatabaseUrlComponents)) {
            return false;
        }
        DatabaseUrlComponents other = (DatabaseUrlComponents)o;
        DBType this$type = this.getType();
        DBType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        String this$host = this.getHost();
        String other$host = other.getHost();
        if (this$host == null ? other$host != null : !this$host.equals(other$host)) {
            return false;
        }
        String this$databaseName = this.getDatabaseName();
        String other$databaseName = other.getDatabaseName();
        return !(this$databaseName == null ? other$databaseName != null : !this$databaseName.equals(other$databaseName));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        DBType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        String $host = this.getHost();
        result = result * 59 + ($host == null ? 43 : $host.hashCode());
        String $databaseName = this.getDatabaseName();
        result = result * 59 + ($databaseName == null ? 43 : $databaseName.hashCode());
        return result;
    }

    public String toString() {
        return "DatabaseUrlComponents(type=" + String.valueOf((Object)this.getType()) + ", host=" + this.getHost() + ", databaseName=" + this.getDatabaseName() + ")";
    }

    public DatabaseUrlComponents(DBType type, String host2, String databaseName) {
        this.type = type;
        this.host = host2;
        this.databaseName = databaseName;
    }
}

