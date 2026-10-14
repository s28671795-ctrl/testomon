/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.AuthTokenData;
import org.omg.ATLAS.IllegalTokenRequest;
import org.omg.ATLAS.TokenOkay;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.IdentityToken;

public interface AuthTokenDispenserOperations {
    public AuthTokenData get_my_authorization_token() throws IllegalTokenRequest;

    public AuthTokenData translate_authorization_token(IdentityToken var1, AuthorizationElement[] var2) throws IllegalTokenRequest, TokenOkay;
}

