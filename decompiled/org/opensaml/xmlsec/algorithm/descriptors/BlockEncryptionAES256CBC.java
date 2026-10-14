/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.BlockEncryptionAlgorithm;

public final class BlockEncryptionAES256CBC
implements BlockEncryptionAlgorithm {
    @Override
    @Nonnull
    public String getKey() {
        return "AES";
    }

    @Override
    @Nonnull
    public String getURI() {
        return "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.BlockEncryption;
    }

    @Override
    @Nonnull
    public String getJCAAlgorithmID() {
        return String.format("%s/%s/%s", this.getKey(), this.getCipherMode(), this.getPadding());
    }

    @Override
    @Nonnull
    public Integer getKeyLength() {
        return 256;
    }

    @Override
    @Nonnull
    public String getCipherMode() {
        return "CBC";
    }

    @Override
    @Nonnull
    public String getPadding() {
        return "ISO10126Padding";
    }
}

