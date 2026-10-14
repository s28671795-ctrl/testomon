/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.serializer;

public interface Serializer {
    public String serializeToString(Object var1);

    public byte[] serializeToBytes(Object var1);

    public Object deserializeFromString(String var1);

    public Object deserializeFromBytes(byte[] var1);
}

