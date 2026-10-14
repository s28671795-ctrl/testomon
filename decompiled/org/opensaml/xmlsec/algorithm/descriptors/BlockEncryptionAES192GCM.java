/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.BlockEncryptionAlgorithm;

public final class BlockEncryptionAES192GCM
implements BlockEncryptionAlgorithm {
    @Override
    @Nonnull
    public String getKey() {
        return "AES";
    }

    @Override
    @Nonnull
    public String getURI() {
        return "http://www.w3.org/2009/xmlenc11#aes192-gcm";
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
        return 192;
    }

    @Override
    @Nonnull
    public String getCipherMode() {
        return "GCM";
    }

    @Override
    @Nonnull
    public String getPadding() {
        return "NoPadding";
    }
}

