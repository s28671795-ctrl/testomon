/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.loading;

import com.incoresoft.loading.LoadingStage;
import com.incoresoft.loading.LoadingStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AppLoadingListener {
    public LoadingStage getCurrentLoadingStage();

    public void onStageChanged(@NotNull LoadingStage var1);

    public void onCurrentStageStateChange(@NotNull LoadingStage var1, @NotNull LoadingStatus var2, @Nullable String var3);

    public void onAppLoaded(Runnable var1);
}

