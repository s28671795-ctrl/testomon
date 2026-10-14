/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.IdentityToken;

public final class EstablishContext
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public long client_context_id;
    public AuthorizationElement[] authorization_token;
    public IdentityToken identity_token;
    public byte[] client_authentication_token;

    public EstablishContext() {
    }

    public EstablishContext(long client_context_id, AuthorizationElement[] authorization_token, IdentityToken identity_token, byte[] client_authentication_token) {
        this.client_context_id = client_context_id;
        this.authorization_token = authorization_token;
        this.identity_token = identity_token;
        this.client_authentication_token = client_authentication_token;
    }
}

