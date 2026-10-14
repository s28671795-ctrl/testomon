/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.config;

public interface Configuration {
    public <T> T get(Class<T> var1, String var2);

    public <T, I extends T> void register(Class<T> var1, I var2, String var3);

    public <T> T deregister(Class<T> var1, String var2);
}

