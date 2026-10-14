/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security.jce.derivationfunction;

import com.hierynomus.security.SecurityException;
import com.hierynomus.security.jce.JceDerivationFunction;
import com.hierynomus.security.jce.derivationfunction.CounterDerivationParameters;
import com.hierynomus.security.jce.derivationfunction.DerivationParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class KDFCounterHMacSHA256
extends JceDerivationFunction {
    private Mac mac = Mac.getInstance("HmacSHA256");
    private byte[] fixedSuffix;
    private int maxLength;

    @Override
    public void init(DerivationParameters parameters) throws SecurityException {
        if (!(parameters instanceof CounterDerivationParameters)) {
            throw new IllegalArgumentException("Parameters should be a CounterDerivationParameters");
        }
        CounterDerivationParameters p = (CounterDerivationParameters)parameters;
        SecretKeySpec seed = new SecretKeySpec(p.getSeed(), "HmacSHA256");
        try {
            this.mac.init(seed);
        }
        catch (InvalidKeyException ex) {
            throw new SecurityException(ex);
        }
        this.fixedSuffix = p.getFixedCounterSuffix();
        this.maxLength = p.getCounterLength();
    }

    @Override
    public int generateBytes(byte[] out, int outOff, int len) {
        int generated = 0;
        int rounds = len / 32;
        if (len % 32 != 0) {
            ++rounds;
        }
        byte[] input = new byte[4];
        for (int i = 0; i < rounds; ++i) {
            input[0] = (byte)(i + 1 >>> 24);
            input[1] = (byte)(i + 1 >>> 16);
            input[2] = (byte)(i + 1 >>> 8);
            input[3] = (byte)(i + 1);
            this.mac.update(input);
            this.mac.update(this.fixedSuffix);
            byte[] tmp = this.mac.doFinal();
            int toCopy = tmp.length;
            if (tmp.length + generated > len) {
                toCopy = len - generated;
            }
            System.arraycopy(tmp, 0, out, outOff, toCopy);
            generated += toCopy;
            outOff += toCopy;
        }
        return len;
    }
}

