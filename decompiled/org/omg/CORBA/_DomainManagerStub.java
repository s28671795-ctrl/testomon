/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.DomainManagerOperations;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import org.omg.CORBA.portable.ServantObjectExt;

public class _DomainManagerStub
extends ObjectImpl
implements DomainManager {
    private static final long serialVersionUID = 1L;
    private String[] ids = new String[]{"IDL:omg.org/CORBA/DomainManager:1.0"};
    public static final Class _opsClass = DomainManagerOperations.class;

    @Override
    public String[] _ids() {
        return this.ids;
    }

    /*
     * Loose catch block
     */
    @Override
    public Policy get_domain_policy(int policy_type) {
        ServantObject _so;
        while (true) {
            if (!this._is_local()) {
                Policy policy;
                OutputStream _os;
                InputStream _is;
                block27: {
                    Policy _result;
                    _is = null;
                    _os = null;
                    _os = this._request("get_domain_policy", true);
                    _os.write_ulong(policy_type);
                    _is = this._invoke(_os);
                    policy = _result = PolicyHelper.read(_is);
                    if (_os == null) break block27;
                    try {
                        _os.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                }
                this._releaseReply(_is);
                return policy;
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
                        _ax.getInputStream().close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException("Unexpected exception " + e.toString());
                    }
                    throw new RuntimeException("Unexpected exception " + _id);
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
            _so = this._servant_preinvoke("get_domain_policy", _opsClass);
            if (_so != null) break;
        }
        DomainManagerOperations _localServant = (DomainManagerOperations)_so.servant;
        try {
            Policy _result = _localServant.get_domain_policy(policy_type);
            if (_so instanceof ServantObjectExt) {
                ((ServantObjectExt)_so).normalCompletion();
            }
            Policy _id = _result;
            return _id;
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

