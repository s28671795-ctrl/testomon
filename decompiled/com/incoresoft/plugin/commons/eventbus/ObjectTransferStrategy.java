/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.eventbus;

public interface ObjectTransferStrategy {
    public <T> T toObject(byte[] var1, Class<T> var2);

    public <T> byte[] toByteArray(T var1, Class<T> var2);
}

