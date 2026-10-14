/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.DomainManagerHelper;
import org.omg.CORBA.DomainManagerOperations;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.PolicyHelper;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class DomainManagerPOA
extends Servant
implements InvokeHandler,
DomainManagerOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/DomainManager:1.0"};

    public DomainManager _this() {
        Object __o = this._this_object();
        DomainManager __r = DomainManagerHelper.narrow(__o);
        return __r;
    }

    public DomainManager _this(ORB orb) {
        Object __o = this._this_object(orb);
        DomainManager __r = DomainManagerHelper.narrow(__o);
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
                int _arg0 = _input.read_ulong();
                _out = handler.createReply();
                PolicyHelper.write(_out, this.get_domain_policy(_arg0));
                break;
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("get_domain_policy", 0);
    }
}

