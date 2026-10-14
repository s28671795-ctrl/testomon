/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public interface AlgorithmDescriptor {
    @Nonnull
    @NotEmpty
    public String getURI();

    @Nonnull
    public AlgorithmType getType();

    @Nonnull
    @NotEmpty
    public String getJCAAlgorithmID();

    public static enum AlgorithmType {
        BlockEncryption,
        Mac,
        MessageDigest,
        KeyAgreement,
        KeyTransport,
        Signature,
        SymmetricKeyWrap;

    }
}

