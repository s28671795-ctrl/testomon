/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security.jce;

import com.hierynomus.security.AEADBlockCipher;
import com.hierynomus.security.Cipher;
import com.hierynomus.security.SecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class JceAEADCipher
implements AEADBlockCipher {
    private Cipher cipher;

    JceAEADCipher(String name, Provider jceProvider, String providerName) throws SecurityException {
        try {
            this.cipher = jceProvider != null ? Cipher.getInstance(name, jceProvider) : (providerName != null ? Cipher.getInstance(name, providerName) : Cipher.getInstance(name));
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public void init(Cipher.CryptMode cryptMode, byte[] bytes, GCMParameterSpec gcmParameterSpec) throws SecurityException {
        try {
            if (Cipher.CryptMode.DECRYPT == cryptMode) {
                this.cipher.init(2, (Key)new SecretKeySpec(bytes, this.cipher.getAlgorithm().split("/")[0]), gcmParameterSpec);
            } else {
                this.cipher.init(1, (Key)new SecretKeySpec(bytes, this.cipher.getAlgorithm().split("/")[0]), gcmParameterSpec);
            }
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public void updateAAD(byte[] aad, int aadOffset, int aadLength) throws SecurityException {
        this.cipher.updateAAD(aad, aadOffset, aadLength);
    }

    @Override
    public byte[] update(byte[] in, int inOffset, int inLength) throws SecurityException {
        return this.cipher.update(in, inOffset, inLength);
    }

    @Override
    public byte[] doFinal(byte[] in, int inOffset, int inLength) throws SecurityException {
        try {
            return this.cipher.doFinal(in, inOffset, inLength);
        }
        catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public void reset() {
    }
}

