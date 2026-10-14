/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ConstructionPolicy;
import org.omg.CORBA.ConstructionPolicyHelper;
import org.omg.CORBA.ConstructionPolicyOperations;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefHelper;
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

public abstract class ConstructionPolicyPOA
extends Servant
implements InvokeHandler,
ConstructionPolicyOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/ConstructionPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    public ConstructionPolicy _this() {
        Object __o = this._this_object();
        ConstructionPolicy __r = ConstructionPolicyHelper.narrow(__o);
        return __r;
    }

    public ConstructionPolicy _this(ORB orb) {
        Object __o = this._this_object(orb);
        ConstructionPolicy __r = ConstructionPolicyHelper.narrow(__o);
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
                _out = handler.createReply();
                _out.write_ulong(this.policy_type());
                break;
            }
            case 1: {
                _out = handler.createReply();
                PolicyHelper.write(_out, this.copy());
                break;
            }
            case 2: {
                InterfaceDef _arg0 = InterfaceDefHelper.read(_input);
                boolean _arg1 = _input.read_boolean();
                _out = handler.createReply();
                this.make_domain_manager(_arg0, _arg1);
                break;
            }
            case 3: {
                _out = handler.createReply();
                this.destroy();
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("_get_policy_type", 0);
        m_opsHash.put("copy", 1);
        m_opsHash.put("make_domain_manager", 2);
        m_opsHash.put("destroy", 3);
    }
}

