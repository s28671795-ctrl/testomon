/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security;

import com.hierynomus.security.Cipher;
import com.hierynomus.security.SecurityException;
import javax.crypto.spec.GCMParameterSpec;

public interface AEADBlockCipher {
    public void init(Cipher.CryptMode var1, byte[] var2, GCMParameterSpec var3) throws SecurityException;

    public void updateAAD(byte[] var1, int var2, int var3) throws SecurityException;

    public byte[] update(byte[] var1, int var2, int var3) throws SecurityException;

    public byte[] doFinal(byte[] var1, int var2, int var3) throws SecurityException;

    public void reset();
}

