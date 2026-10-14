/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws;

import com.incoresoft.vezha.backup.ws.message.BackupWSMessage;

public class DatabaseTablePopulatedStageMessage
implements BackupWSMessage {
    private String tableName;

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DatabaseTablePopulatedStageMessage)) {
            return false;
        }
        DatabaseTablePopulatedStageMessage other = (DatabaseTablePopulatedStageMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$tableName = this.getTableName();
        String other$tableName = other.getTableName();
        return !(this$tableName == null ? other$tableName != null : !this$tableName.equals(other$tableName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof DatabaseTablePopulatedStageMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $tableName = this.getTableName();
        result = result * 59 + ($tableName == null ? 43 : $tableName.hashCode());
        return result;
    }

    public String toString() {
        return "DatabaseTablePopulatedStageMessage(tableName=" + this.getTableName() + ")";
    }

    public DatabaseTablePopulatedStageMessage() {
    }

    public DatabaseTablePopulatedStageMessage(String tableName) {
        this.tableName = tableName;
    }
}

