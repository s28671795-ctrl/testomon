/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.AuthTokenData;
import org.omg.ATLAS.AuthTokenDispenser;
import org.omg.ATLAS.AuthTokenDispenserHelper;
import org.omg.ATLAS.AuthTokenDispenserOperations;
import org.omg.ATLAS.AuthTokenDispenserPOA;
import org.omg.ATLAS.IllegalTokenRequest;
import org.omg.ATLAS.TokenOkay;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.IdentityToken;
import org.omg.PortableServer.POA;

public class AuthTokenDispenserPOATie
extends AuthTokenDispenserPOA {
    private AuthTokenDispenserOperations _delegate;
    private POA _poa;

    public AuthTokenDispenserPOATie(AuthTokenDispenserOperations delegate) {
        this._delegate = delegate;
    }

    public AuthTokenDispenserPOATie(AuthTokenDispenserOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public AuthTokenDispenser _this() {
        Object __o = this._this_object();
        AuthTokenDispenser __r = AuthTokenDispenserHelper.narrow(__o);
        return __r;
    }

    @Override
    public AuthTokenDispenser _this(ORB orb) {
        Object __o = this._this_object(orb);
        AuthTokenDispenser __r = AuthTokenDispenserHelper.narrow(__o);
        return __r;
    }

    public AuthTokenDispenserOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(AuthTokenDispenserOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public POA _default_POA() {
        if (this._poa != null) {
            return this._poa;
        }
        return super._default_POA();
    }

    @Override
    public AuthTokenData get_my_authorization_token() throws IllegalTokenRequest {
        return this._delegate.get_my_authorization_token();
    }

    @Override
    public AuthTokenData translate_authorization_token(IdentityToken the_subject, AuthorizationElement[] the_token) throws IllegalTokenRequest, TokenOkay {
        return this._delegate.translate_authorization_token(the_subject, the_token);
    }
}

