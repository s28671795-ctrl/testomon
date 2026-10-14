/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm.descriptors;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;
import org.opensaml.xmlsec.algorithm.KeyTransportAlgorithm;

public final class KeyTransportRSA15
implements KeyTransportAlgorithm {
    @Override
    @Nonnull
    @NotEmpty
    public String getKey() {
        return "RSA";
    }

    @Override
    @Nonnull
    @NotEmpty
    public String getURI() {
        return "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
    }

    @Override
    @Nonnull
    public AlgorithmDescriptor.AlgorithmType getType() {
        return AlgorithmDescriptor.AlgorithmType.KeyTransport;
    }

    @Override
    @Nonnull
    @NotEmpty
    public String getJCAAlgorithmID() {
        return String.format("%s/%s/%s", this.getKey(), this.getCipherMode(), this.getPadding());
    }

    @Override
    @Nonnull
    @NotEmpty
    public String getCipherMode() {
        return "ECB";
    }

    @Override
    @Nonnull
    @NotEmpty
    public String getPadding() {
        return "PKCS1Padding";
    }
}

