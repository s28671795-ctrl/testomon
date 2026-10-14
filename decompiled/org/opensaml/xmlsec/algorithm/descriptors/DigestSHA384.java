/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.DigestAlgorithm;

public final class DigestSHA384
implements DigestAlgorithm {
    @Override
    @Nonnull
    public String getURI() {
        return "http://www.w3.org/2001/04/xmldsig-more#sha384";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.MessageDigest;
    }

    @Override
    @Nonnull
    public String getJCAAlgorithmID() {
        return "SHA-384";
    }
}

