/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.ATLAS.AuthTokenData;
import org.omg.ATLAS.AuthTokenDataHelper;
import org.omg.ATLAS.AuthTokenDispenser;
import org.omg.ATLAS.AuthTokenDispenserOperations;
import org.omg.ATLAS.IllegalTokenRequest;
import org.omg.ATLAS.IllegalTokenRequestHelper;
import org.omg.ATLAS.TokenOkay;
import org.omg.ATLAS.TokenOkayHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;
import org.omg.CSI.AuthorizationElement;
import org.omg.CSI.AuthorizationTokenHelper;
import org.omg.CSI.IdentityToken;
import org.omg.CSI.IdentityTokenHelper;

public class _AuthTokenDispenserStub
extends ObjectImpl
implements AuthTokenDispenser {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/ATLAS/AuthTokenDispenser:1.0"};
    public static final Class _opsClass = AuthTokenDispenserOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }

    /*
     * Loose catch block
     */
    @Override
    public AuthTokenData get_my_authorization_token() throws IllegalTokenRequest {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                AuthTokenData authTokenData;
                OutputStream _os;
                InputStream _is;
                block32: {
                    AuthTokenData _result;
                    _is = null;
                    _os = null;
                    _os = this._request("get_my_authorization_token", true);
                    _is = this._invoke(_os);
                    authTokenData = _result = AuthTokenDataHelper.read(_is);
                    if (_os == null) break block32;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return authTokenData;
                catch (RemarshalException _rx) {
                    if (_os != null) {
                        try {
                            _os.close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                    }
                    this._releaseReply(_is);
                }
                catch (ApplicationException _ax) {
                    String _id = _ax.getId();
                    try {
                        if (_id.equals("IDL:omg.org/ATLAS/IllegalTokenRequest:1.0")) {
                            throw IllegalTokenRequestHelper.read(_ax.getInputStream());
                        }
                        throw new RuntimeException("Unexpected exception " + _id);
                    }
                    catch (Throwable throwable) {
                        try {
                            _ax.getInputStream().close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                        throw throwable;
                    }
                    {
                        catch (Throwable throwable) {
                            if (_os != null) {
                                try {
                                    _os.close();
                                }
                                catch (IOException e) {
                                    throw new RuntimeException("Unexpected exception " + e.toString());
                                }
                            }
                            this._releaseReply(_is);
                            throw throwable;
                        }
                    }
                }
            }
            _so = this._servant_preinvoke("get_my_authorization_token", _opsClass);
            if (_so != null) break;
        }
        AuthTokenDispenserOperations _localServant = (AuthTokenDispenserOperations)_so.servant;
        try {
            AuthTokenData _result = _localServant.get_my_authorization_token();
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            AuthTokenData _id = _result;
            return _id;
        }
        catch (IllegalTokenRequest ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (RuntimeException re) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(re);
            }
            throw re;
        }
        catch (Error err) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(err);
            }
            throw err;
        }
        finally {
            this._servant_postinvoke(_so);
        }
    }

    /*
     * Loose catch block
     */
    @Override
    public AuthTokenData translate_authorization_token(IdentityToken the_subject, AuthorizationElement[] the_token) throws IllegalTokenRequest, TokenOkay {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                AuthTokenData authTokenData;
                OutputStream _os;
                InputStream _is;
                block35: {
                    AuthTokenData _result;
                    _is = null;
                    _os = null;
                    _os = this._request("translate_authorization_token", true);
                    IdentityTokenHelper.write(_os, the_subject);
                    AuthorizationTokenHelper.write(_os, the_token);
                    _is = this._invoke(_os);
                    authTokenData = _result = AuthTokenDataHelper.read(_is);
                    if (_os == null) break block35;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return authTokenData;
                catch (RemarshalException _rx) {
                    if (_os != null) {
                        try {
                            _os.close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                    }
                    this._releaseReply(_is);
                }
                catch (ApplicationException _ax) {
                    String _id = _ax.getId();
                    try {
                        if (_id.equals("IDL:omg.org/ATLAS/IllegalTokenRequest:1.0")) {
                            throw IllegalTokenRequestHelper.read(_ax.getInputStream());
                        }
                        if (_id.equals("IDL:omg.org/ATLAS/TokenOkay:1.0")) {
                            throw TokenOkayHelper.read(_ax.getInputStream());
                        }
                        throw new RuntimeException("Unexpected exception " + _id);
                    }
                    catch (Throwable throwable) {
                        try {
                            _ax.getInputStream().close();
                        }
                        catch (IOException e) {
                            throw new RuntimeException("Unexpected exception " + e.toString());
                        }
                        throw throwable;
                    }
                    {
                        catch (Throwable throwable) {
                            if (_os != null) {
                                try {
                                    _os.close();
                                }
                                catch (IOException e) {
                                    throw new RuntimeException("Unexpected exception " + e.toString());
                                }
                            }
                            this._releaseReply(_is);
                            throw throwable;
                        }
                    }
                }
            }
            _so = this._servant_preinvoke("translate_authorization_token", _opsClass);
            if (_so != null) break;
        }
        AuthTokenDispenserOperations _localServant = (AuthTokenDispenserOperations)_so.servant;
        try {
            AuthTokenData _result = _localServant.translate_authorization_token(the_subject, the_token);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            AuthTokenData _id = _result;
            return _id;
        }
        catch (IllegalTokenRequest ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (TokenOkay ex) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(ex);
            }
            throw ex;
        }
        catch (RuntimeException re) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(re);
            }
            throw re;
        }
        catch (Error err) {
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).exceptionalCompletion(err);
            }
            throw err;
        }
        finally {
            this._servant_postinvoke(_so);
        }
    }
}

