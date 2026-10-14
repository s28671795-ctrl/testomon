/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.ArrayDef;
import org.omg.CORBA.ArrayDefHelper;
import org.omg.CORBA.ArrayDefOperations;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class ArrayDefPOA
extends Servant
implements InvokeHandler,
ArrayDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/ArrayDef:1.0", "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public ArrayDef _this() {
        Object __o = this._this_object();
        ArrayDef __r = ArrayDefHelper.narrow(__o);
        return __r;
    }

    public ArrayDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        ArrayDef __r = ArrayDefHelper.narrow(__o);
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
                _out.write_TypeCode(this.element_type());
                break;
            }
            case 1: {
                _out = handler.createReply();
                _out.write_ulong(this.length());
                break;
            }
            case 2: {
                _out = handler.createReply();
                IDLTypeHelper.write(_out, this.element_type_def());
                break;
            }
            case 3: {
                _out = handler.createReply();
                this.length(_input.read_ulong());
                break;
            }
            case 4: {
                _out = handler.createReply();
                _out.write_TypeCode(this.type());
                break;
            }
            case 5: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 6: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 7: {
                _out = handler.createReply();
                this.element_type_def(IDLTypeHelper.read(_input));
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("_get_element_type", 0);
        m_opsHash.put("_get_length", 1);
        m_opsHash.put("_get_element_type_def", 2);
        m_opsHash.put("_set_length", 3);
        m_opsHash.put("_get_type", 4);
        m_opsHash.put("destroy", 5);
        m_opsHash.put("_get_def_kind", 6);
        m_opsHash.put("_set_element_type_def", 7);
    }
}

