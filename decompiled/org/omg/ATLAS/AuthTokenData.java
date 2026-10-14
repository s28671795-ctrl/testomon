/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.IdentityToken;
import org.omg.TimeBase.UtcT;

public final class AuthTokenData
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public IdentityToken[] ident_token;
    public AuthorizationElement[] auth_token;
    public UtcT[] expiry_time;

    public AuthTokenData() {
    }

    public AuthTokenData(IdentityToken[] ident_token, AuthorizationElement[] auth_token, UtcT[] expiry_time) {
        this.ident_token = ident_token;
        this.auth_token = auth_token;
        this.expiry_time = expiry_time;
    }
}

