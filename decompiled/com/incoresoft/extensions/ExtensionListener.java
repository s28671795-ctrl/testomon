/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.extensions;

import com.incoresoft.middleware.plugin.InitializableExtension;

public interface ExtensionListener {
    public void onExtensionInit(InitializableExtension<?> var1, String var2);

    public void onExtensionTerminate(InitializableExtension<?> var1, String var2);
}

