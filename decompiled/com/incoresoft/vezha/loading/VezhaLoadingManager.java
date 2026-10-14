/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.loading;

import com.incoresoft.loading.LoadingManager;
import com.incoresoft.loading.LoadingStage;
import java.util.List;

public class VezhaLoadingManager
extends LoadingManager {
    @Override
    public List<LoadingStage> getStages() {
        return List.of(LoadingStage.values());
    }
}

