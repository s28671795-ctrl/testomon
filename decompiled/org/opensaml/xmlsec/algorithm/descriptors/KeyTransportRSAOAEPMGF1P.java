/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.KeyTransportAlgorithm;

public final class KeyTransportRSAOAEPMGF1P
implements KeyTransportAlgorithm {
    @Override
    @Nonnull
    public String getKey() {
        return "RSA";
    }

    @Override
    @Nonnull
    public String getURI() {
        return "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.KeyTransport;
    }

    @Override
    @Nonnull
    public String getJCAAlgorithmID() {
        return String.format("%s/%s/%s", this.getKey(), this.getCipherMode(), this.getPadding());
    }

    @Override
    @Nonnull
    public String getCipherMode() {
        return "ECB";
    }

    @Override
    @Nonnull
    public String getPadding() {
        return "OAEPPadding";
    }
}

