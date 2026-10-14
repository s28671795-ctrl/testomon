/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

public interface JavaObjectSerializer {
    public byte[] serialize(Object var1) throws Exception;

    public Object deserialize(byte[] var1) throws Exception;
}

