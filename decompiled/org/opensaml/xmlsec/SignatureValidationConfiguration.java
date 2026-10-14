/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;

public interface SignatureValidationConfiguration
extends WhitelistBlacklistConfiguration {
    @Nullable
    public SignatureTrustEngine getSignatureTrustEngine();
}

