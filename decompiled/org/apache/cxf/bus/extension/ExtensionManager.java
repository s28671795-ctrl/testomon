/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bus.extension;

public interface ExtensionManager {
    public void activateAll();

    public <T> void activateAllByType(Class<T> var1);

    public <T> T getExtension(String var1, Class<T> var2);
}

