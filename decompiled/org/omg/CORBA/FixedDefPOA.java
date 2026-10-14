/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.FixedDef;
import org.omg.CORBA.FixedDefHelper;
import org.omg.CORBA.FixedDefOperations;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class FixedDefPOA
extends Servant
implements InvokeHandler,
FixedDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/FixedDef:1.0", "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public FixedDef _this() {
        Object __o = this._this_object();
        FixedDef __r = FixedDefHelper.narrow(__o);
        return __r;
    }

    public FixedDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        FixedDef __r = FixedDefHelper.narrow(__o);
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
                this.scale(_input.read_short());
                break;
            }
            case 1: {
                _out = handler.createReply();
                this.digits(_input.read_ushort());
                break;
            }
            case 2: {
                _out = handler.createReply();
                _out.write_ushort(this.digits());
                break;
            }
            case 3: {
                _out = handler.createReply();
                _out.write_TypeCode(this.type());
                break;
            }
            case 4: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 5: {
                _out = handler.createReply();
                _out.write_short(this.scale());
                break;
            }
            case 6: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("_set_scale", 0);
        m_opsHash.put("_set_digits", 1);
        m_opsHash.put("_get_digits", 2);
        m_opsHash.put("_get_type", 3);
        m_opsHash.put("destroy", 4);
        m_opsHash.put("_get_scale", 5);
        m_opsHash.put("_get_def_kind", 6);
    }
}

