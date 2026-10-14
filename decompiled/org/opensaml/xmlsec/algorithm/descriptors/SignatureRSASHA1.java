/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.SignatureAlgorithm;

public final class SignatureRSASHA1
implements SignatureAlgorithm {
    @Override
    @Nonnull
    public String getKey() {
        return "RSA";
    }

    @Override
    @Nonnull
    public String getURI() {
        return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.Signature;
    }

    @Override
    @Nonnull
    public String getJCAAlgorithmID() {
        return "SHA1withRSA";
    }

    @Override
    @Nonnull
    public String getDigest() {
        return "SHA-1";
    }
}

