/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import com.incoresoft.vezha.backup.BackupStageUtils;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public enum BackupCreationStage {
    FILES_COPYING,
    DATABASE_SCHEMA_CREATION,
    DATABASE_POPULATION,
    METADATA_CREATION,
    FILES_ARCHIVING;

    private static final Map<BackupCreationStage, BackupCreationStage> STAGE_TO_PREVIOUS_STAGE_MAP;
    private static final Map<BackupCreationStage, Integer> STAGE_TO_STAGE_NUMBER_MAP;

    public int getNumber() {
        return STAGE_TO_STAGE_NUMBER_MAP.getOrDefault((Object)this, -1);
    }

    public BackupCreationStage getPrevious() {
        return STAGE_TO_PREVIOUS_STAGE_MAP.get((Object)this);
    }

    public static int getStagesNumber() {
        return BackupCreationStage.values().length;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

    static {
        Pair maps = BackupStageUtils.initStageMaps((Enum[])BackupCreationStage.values());
        STAGE_TO_PREVIOUS_STAGE_MAP = (Map)maps.getLeft();
        STAGE_TO_STAGE_NUMBER_MAP = (Map)maps.getRight();
    }
}

