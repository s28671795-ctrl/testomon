/*
 * Decompiled with CFR 0.152.
 */
package com.nimbusds.jose.proc;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.List;

public class JWKSecurityContext
implements SecurityContext {
    private final List<JWK> keys;

    public JWKSecurityContext(List<JWK> keys2) {
        this.keys = keys2;
        if (keys2 == null) {
            throw new IllegalArgumentException("The list of keys must not be null");
        }
    }

    public List<JWK> getKeys() {
        return this.keys;
    }
}

