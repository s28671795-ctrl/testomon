/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.MACAlgorithm;

public final class HMACSHA1
implements MACAlgorithm {
    @Override
    @Nonnull
    public String getURI() {
        return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.Mac;
    }

    @Override
    @Nonnull
    public String getJCAAlgorithmID() {
        return "HmacSHA1";
    }

    @Override
    @Nonnull
    public String getDigest() {
        return "SHA-1";
    }
}

