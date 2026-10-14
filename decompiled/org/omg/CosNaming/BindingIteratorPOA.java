/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CosNaming.BindingHelper;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingIterator;
import org.omg.CosNaming.BindingIteratorHelper;
import org.omg.CosNaming.BindingIteratorOperations;
import org.omg.CosNaming.BindingListHelper;
import org.omg.CosNaming.BindingListHolder;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class BindingIteratorPOA
extends Servant
implements InvokeHandler,
BindingIteratorOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CosNaming/BindingIterator:1.0"};

    public BindingIterator _this() {
        Object __o = this._this_object();
        BindingIterator __r = BindingIteratorHelper.narrow(__o);
        return __r;
    }

    public BindingIterator _this(ORB orb) {
        Object __o = this._this_object(orb);
        BindingIterator __r = BindingIteratorHelper.narrow(__o);
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
                BindingHolder _arg0 = new BindingHolder();
                _out = handler.createReply();
                _out.write_boolean(this.next_one(_arg0));
                BindingHelper.write(_out, _arg0.value);
                break;
            }
            case 1: {
                int _arg0 = _input.read_ulong();
                BindingListHolder _arg1 = new BindingListHolder();
                _out = handler.createReply();
                _out.write_boolean(this.next_n(_arg0, _arg1));
                BindingListHelper.write(_out, _arg1.value);
                break;
            }
            case 2: {
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
        m_opsHash.put("next_one", 0);
        m_opsHash.put("next_n", 1);
        m_opsHash.put("destroy", 2);
    }
}

