/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;

public final class SignatureECDSASHA224
implements SignatureAlgorithm {
    @Override
    @Nonnull
    public String getKey() {
        return "EC";
    }

    @Override
    @Nonnull
    public String getURI() {
        return "http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.Signature;
    }

    @Override
    @Nonnull
    public String getJCAAlgorithmID() {
        return "SHA224withECDSA";
    }

    @Override
    @Nonnull
    public String getDigest() {
        return "SHA-224";
    }
}

