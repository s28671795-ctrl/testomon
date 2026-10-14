/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.crypto;

import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class KeyProperties {
    private String keyAlgo;
    private int keySize;
    private int blockSize = -1;
    private byte[] additionalData;
    private SecureRandom secureRandom;
    private AlgorithmParameterSpec algoSpec;
    private boolean compressionSupported;

    public KeyProperties() {
    }

    public KeyProperties(String keyAlgo) {
        this(keyAlgo, -1);
    }

    public KeyProperties(String keyAlgo, int keySize) {
        this.keyAlgo = keyAlgo;
        this.keySize = keySize;
    }

    public String getKeyAlgo() {
        return this.keyAlgo;
    }

    public void setKeyAlgo(String keyAlgo) {
        this.keyAlgo = keyAlgo;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public AlgorithmParameterSpec getAlgoSpec() {
        return this.algoSpec;
    }

    public void setAlgoSpec(AlgorithmParameterSpec algoSpec) {
        this.algoSpec = algoSpec;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public boolean isCompressionSupported() {
        return this.compressionSupported;
    }

    public void setCompressionSupported(boolean compressionSupported) {
        this.compressionSupported = compressionSupported;
    }

    public byte[] getAdditionalData() {
        return this.additionalData;
    }

    public void setAdditionalData(byte[] additionalData) {
        this.additionalData = additionalData;
    }
}

