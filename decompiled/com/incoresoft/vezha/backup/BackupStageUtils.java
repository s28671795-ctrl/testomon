/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class BackupStageUtils {
    public static <S extends Enum<S>> Pair<Map<S, S>, Map<S, Integer>> initStageMaps(S[] allStages) {
        HashMap<S, S> stageToPreviousStageMap = new HashMap<S, S>();
        HashMap<S, Integer> stageToStageNumberMap = new HashMap<S, Integer>();
        for (int i = 0; i < allStages.length; ++i) {
            S stage = allStages[i];
            stageToStageNumberMap.put(stage, i + 1);
            if (i <= 0) continue;
            S previous = allStages[i - 1];
            stageToPreviousStageMap.put(stage, previous);
        }
        return Pair.of(Collections.unmodifiableMap(stageToPreviousStageMap), Collections.unmodifiableMap(stageToStageNumberMap));
    }
}

