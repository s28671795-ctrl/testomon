/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.cleaner;

import com.incoresoft.middleware.cleaner.CleaningCategory;

public interface CleanerListener {
    public void onCleanStarted();

    public void onDatabaseTaskExecuted(CleaningCategory var1, String var2, boolean var3);

    public void onStorageTaskExecuted(CleaningCategory var1, String var2, boolean var3);

    public void onCustomTaskExecuted();

    public void onCleanFinished();
}

