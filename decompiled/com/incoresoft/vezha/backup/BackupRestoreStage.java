/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.incoresoft.vezha.backup.BackupStageUtils;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public enum BackupRestoreStage {
    DATABASE_DATA_DELETING,
    DATABASE_POPULATION,
    CONFIGURATION_FILES_IMPORT;

    private static final Map<BackupRestoreStage, BackupRestoreStage> STAGE_TO_PREVIOUS_STAGE_MAP;
    private static final Map<BackupRestoreStage, Integer> STAGE_TO_STAGE_NUMBER_MAP;

    public int getNumber() {
        return STAGE_TO_STAGE_NUMBER_MAP.getOrDefault((Object)this, -1);
    }

    public BackupRestoreStage getPrevious() {
        return STAGE_TO_PREVIOUS_STAGE_MAP.get((Object)this);
    }

    public static int getStagesNumber() {
        return BackupRestoreStage.values().length;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    static {
        Pair maps = BackupStageUtils.initStageMaps((Enum[])BackupRestoreStage.values());
        STAGE_TO_PREVIOUS_STAGE_MAP = (Map)maps.getLeft();
        STAGE_TO_STAGE_NUMBER_MAP = (Map)maps.getRight();
    }
}

