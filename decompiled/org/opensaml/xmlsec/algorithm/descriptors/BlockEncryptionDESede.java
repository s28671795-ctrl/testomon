/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.BlockEncryptionAlgorithm;

public final class BlockEncryptionDESede
implements BlockEncryptionAlgorithm {
    @Override
    @Nonnull
    @NotEmpty
    public String getKey() {
        return "DESede";
    }

    @Override
    @Nonnull
    @NotEmpty
    public String getURI() {
        return "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.BlockEncryption;
    }

    @Override
    @Nonnull
    @NotEmpty
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
    @NotEmpty
    public String getCipherMode() {
        return "CBC";
    }

    @Override
    @Nonnull
    @NotEmpty
    public String getPadding() {
        return "ISO10126Padding";
    }
}

