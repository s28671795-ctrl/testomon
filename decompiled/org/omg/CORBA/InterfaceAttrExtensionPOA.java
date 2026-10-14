/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefSeqHelper;
import org.omg.CORBA.ExtAttributeDefHelper;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.InterfaceAttrExtension;
import org.omg.CORBA.InterfaceAttrExtensionHelper;
import org.omg.CORBA.InterfaceAttrExtensionOperations;
import org.omg.CORBA.InterfaceAttrExtensionPackage.ExtFullInterfaceDescriptionHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class InterfaceAttrExtensionPOA
extends Servant
implements InvokeHandler,
InterfaceAttrExtensionOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/InterfaceAttrExtension:1.0"};

    public InterfaceAttrExtension _this() {
        Object __o = this._this_object();
        InterfaceAttrExtension __r = InterfaceAttrExtensionHelper.narrow(__o);
        return __r;
    }

    public InterfaceAttrExtension _this(ORB orb) {
        Object __o = this._this_object(orb);
        InterfaceAttrExtension __r = InterfaceAttrExtensionHelper.narrow(__o);
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
                ExtFullInterfaceDescriptionHelper.write(_out, this.describe_ext_interface());
                break;
            }
            case 1: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                AttributeMode _arg4 = AttributeModeHelper.read(_input);
                ExceptionDef[] _arg5 = ExceptionDefSeqHelper.read(_input);
                ExceptionDef[] _arg6 = ExceptionDefSeqHelper.read(_input);
                _out = handler.createReply();
                ExtAttributeDefHelper.write(_out, this.create_ext_attribute(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6));
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
        m_opsHash.put("describe_ext_interface", 0);
        m_opsHash.put("create_ext_attribute", 1);
    }
}

