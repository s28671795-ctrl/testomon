/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security;

public interface BlockCipher {
    public static final int ALIGN = 16;

    public void setKey(byte[] var1);

    public void encrypt(byte[] var1, int var2, int var3);

    public void decrypt(byte[] var1, int var2, int var3);

    public int getKeyLength();
}

