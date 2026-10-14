/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.config.signature;

import com.nimbusds.jose.JWSAlgorithm;
import org.pac4j.core.util.InitializableObject;
import org.pac4j.jwt.config.signature.SignatureConfiguration;

public abstract class AbstractSignatureConfiguration
extends InitializableObject
implements SignatureConfiguration {
    protected JWSAlgorithm algorithm = JWSAlgorithm.HS256;

    public JWSAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(JWSAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}

