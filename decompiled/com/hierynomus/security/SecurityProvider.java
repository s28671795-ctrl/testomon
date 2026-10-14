/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security;

import com.hierynomus.security.AEADBlockCipher;
import com.hierynomus.security.Cipher;
import com.hierynomus.security.DerivationFunction;
import com.hierynomus.security.Mac;
import com.hierynomus.security.MessageDigest;
import com.hierynomus.security.SecurityException;

public interface SecurityProvider {
    public MessageDigest getDigest(String var1) throws SecurityException;

    public Mac getMac(String var1) throws SecurityException;

    public Cipher getCipher(String var1) throws SecurityException;

    public AEADBlockCipher getAEADBlockCipher(String var1) throws SecurityException;

    public DerivationFunction getDerivationFunction(String var1) throws SecurityException;
}

