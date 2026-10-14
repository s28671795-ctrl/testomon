/*
 * Decompiled with CFR 0.152.
 */
package org.omg.BiDirPolicy;

import java.util.HashMap;
import org.omg.BiDirPolicy.BidirectionalPolicy;
import org.omg.BiDirPolicy.BidirectionalPolicyHelper;
import org.omg.BiDirPolicy.BidirectionalPolicyOperations;
import org.omg.CORBA.BAD_OPERATION;
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

public abstract class BidirectionalPolicyPOA
extends Servant
implements InvokeHandler,
BidirectionalPolicyOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/BiDirPolicy/BidirectionalPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    public BidirectionalPolicy _this() {
        Object __o = this._this_object();
        BidirectionalPolicy __r = BidirectionalPolicyHelper.narrow(__o);
        return __r;
    }

    public BidirectionalPolicy _this(ORB orb) {
        Object __o = this._this_object(orb);
        BidirectionalPolicy __r = BidirectionalPolicyHelper.narrow(__o);
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
                _out.write_ushort(this.value());
                break;
            }
            case 1: {
                _out = handler.createReply();
                _out.write_ulong(this.policy_type());
                break;
            }
            case 2: {
                _out = handler.createReply();
                PolicyHelper.write(_out, this.copy());
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
        m_opsHash.put("_get_value", 0);
        m_opsHash.put("_get_policy_type", 1);
        m_opsHash.put("copy", 2);
        m_opsHash.put("destroy", 3);
    }
}

