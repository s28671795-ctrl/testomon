/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.plugin;

import org.pf4j.ExtensionPoint;

public interface InitializableExtension<T>
extends ExtensionPoint {
    public void init(T var1);

    default public void terminate() {
    }
}

