/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.io.Serializable;
import java.security.PublicKey;
import org.apache.wss4j.common.principal.PublicKeyPrincipal;

public class PublicKeyPrincipalImpl
implements Serializable,
PublicKeyPrincipal {
    private static final long serialVersionUID = -7662669773454821344L;
    private PublicKey publicKey;

    public PublicKeyPrincipalImpl(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getName() {
        return this.publicKey.toString();
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}

