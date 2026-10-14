/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.digest;

public interface ByteDigester {
    public byte[] digest(byte[] var1);

    public boolean matches(byte[] var1, byte[] var2);
}

