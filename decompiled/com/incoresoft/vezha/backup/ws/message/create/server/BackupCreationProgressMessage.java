/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws.message.create.server;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.vezha.backup.BackupCreationStage;
import com.incoresoft.vezha.backup.ws.message.create.server.ServerBackupCreationWSMessage;

public class BackupCreationProgressMessage
implements ServerBackupCreationWSMessage {
    @JSONField(name="stage_name")
    private String stageName;
    @JSONField(name="stage_number")
    private int stageNumber;
    @JSONField(name="total_stages_number")
    private int totalStagesNumber;

    public BackupCreationProgressMessage(BackupCreationStage stage) {
        this.stageName = stage.toString();
        this.stageNumber = stage.getNumber();
        this.totalStagesNumber = BackupCreationStage.getStagesNumber();
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
        if (!(o instanceof BackupCreationProgressMessage)) {
            return false;
        }
        BackupCreationProgressMessage other = (BackupCreationProgressMessage)o;
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
        return other instanceof BackupCreationProgressMessage;
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
        return "BackupCreationProgressMessage(stageName=" + this.getStageName() + ", stageNumber=" + this.getStageNumber() + ", totalStagesNumber=" + this.getTotalStagesNumber() + ")";
    }

    public BackupCreationProgressMessage() {
    }
}

