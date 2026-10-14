/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.restore.server;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.backup.BackupRestoreStage;
import com.incoresoft.vezha.backup.ws.message.restore.server.ServerBackupRestoreWSMessage;

public class BackupRestoreProgressMessage
implements ServerBackupRestoreWSMessage {
    @JSONField(name="stage_name")
    private String stageName;
    @JSONField(name="stage_number")
    private int stageNumber;
    @JSONField(name="total_stages_number")
    private int totalStagesNumber;

    public BackupRestoreProgressMessage(BackupRestoreStage stage) {
        this.stageName = stage.toString();
        this.stageNumber = stage.getNumber();
        this.totalStagesNumber = BackupRestoreStage.getStagesNumber();
    }

    public String getStageName() {
        return this.stageName;
    }

    public int getStageNumber() {
        return this.stageNumber;
    }

    public int getTotalStagesNumber() {
        return this.totalStagesNumber;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public void setTotalStagesNumber(int totalStagesNumber) {
        this.totalStagesNumber = totalStagesNumber;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BackupRestoreProgressMessage)) {
            return false;
        }
        BackupRestoreProgressMessage other = (BackupRestoreProgressMessage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getStageNumber() != other.getStageNumber()) {
            return false;
        }
        if (this.getTotalStagesNumber() != other.getTotalStagesNumber()) {
            return false;
        }
        String this$stageName = this.getStageName();
        String other$stageName = other.getStageName();
        return !(this$stageName == null ? other$stageName != null : !this$stageName.equals(other$stageName));
    }

    protected boolean canEqual(Object other) {
        return other instanceof BackupRestoreProgressMessage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStageNumber();
        result = result * 59 + this.getTotalStagesNumber();
        String $stageName = this.getStageName();
        result = result * 59 + ($stageName == null ? 43 : $stageName.hashCode());
        return result;
    }

    public String toString() {
        return "BackupRestoreProgressMessage(stageName=" + this.getStageName() + ", stageNumber=" + this.getStageNumber() + ", totalStagesNumber=" + this.getTotalStagesNumber() + ")";
    }

    public BackupRestoreProgressMessage() {
    }
}

