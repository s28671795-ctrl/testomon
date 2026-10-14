/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.util.HashMap;
import org.omg.ATLAS.AuthTokenDataHelper;
import org.omg.ATLAS.AuthTokenDispenser;
import org.omg.ATLAS.AuthTokenDispenserHelper;
import org.omg.ATLAS.AuthTokenDispenserOperations;
import org.omg.ATLAS.IllegalTokenRequest;
import org.omg.ATLAS.IllegalTokenRequestHelper;
import org.omg.ATLAS.TokenOkay;
import org.omg.ATLAS.TokenOkayHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.AuthorizationTokenHelper;
import org.omg.CSI.IdentityToken;
import org.omg.CSI.IdentityTokenHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class AuthTokenDispenserPOA
extends Servant
implements InvokeHandler,
AuthTokenDispenserOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/ATLAS/AuthTokenDispenser:1.0"};

    public AuthTokenDispenser _this() {
        Object __o = this._this_object();
        AuthTokenDispenser __r = AuthTokenDispenserHelper.narrow(__o);
        return __r;
    }

    public AuthTokenDispenser _this(ORB orb) {
        Object __o = this._this_object(orb);
        AuthTokenDispenser __r = AuthTokenDispenserHelper.narrow(__o);
        return __r;
    }

    @Override
    public OutputStream _invoke(String method2, InputStream _input, ResponseHandler handler) throws SystemException {
        OutputStream _out = null;
        Integer opsIndex = m_opsHash.get(method2);
        if (null == opsIndex) {
            throw new BAD_OPERATION(method2 + " not found");
        }
        switch (opsIndex) {
            case 0: {
                try {
                    _out = handler.createReply();
                    AuthTokenDataHelper.write(_out, this.get_my_authorization_token());
                }
                catch (IllegalTokenRequest _ex0) {
                    _out = handler.createExceptionReply();
                    IllegalTokenRequestHelper.write(_out, _ex0);
                }
                break;
            }
            case 1: {
                try {
                    IdentityToken _arg0 = IdentityTokenHelper.read(_input);
                    AuthorizationElement[] _arg1 = AuthorizationTokenHelper.read(_input);
                    _out = handler.createReply();
                    AuthTokenDataHelper.write(_out, this.translate_authorization_token(_arg0, _arg1));
                    break;
                }
                catch (IllegalTokenRequest _ex0) {
                    _out = handler.createExceptionReply();
                    IllegalTokenRequestHelper.write(_out, _ex0);
                    break;
                }
                catch (TokenOkay _ex1) {
                    _out = handler.createExceptionReply();
                    TokenOkayHelper.write(_out, _ex1);
                }
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("get_my_authorization_token", 0);
        m_opsHash.put("translate_authorization_token", 1);
    }
}

