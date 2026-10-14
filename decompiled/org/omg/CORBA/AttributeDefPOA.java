/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.AttributeDef;
import org.omg.CORBA.AttributeDefHelper;
import org.omg.CORBA.AttributeDefOperations;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class AttributeDefPOA
extends Servant
implements InvokeHandler,
AttributeDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/AttributeDef:1.0", "IDL:omg.org/CORBA/Contained:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public AttributeDef _this() {
        Object __o = this._this_object();
        AttributeDef __r = AttributeDefHelper.narrow(__o);
        return __r;
    }

    public AttributeDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        AttributeDef __r = AttributeDefHelper.narrow(__o);
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
                IDLTypeHelper.write(_out, this.type_def());
                break;
            }
            case 1: {
                _out = handler.createReply();
                RepositoryHelper.write(_out, this.containing_repository());
                break;
            }
            case 2: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 3: {
                _out = handler.createReply();
                this.type_def(IDLTypeHelper.read(_input));
                break;
            }
            case 4: {
                _out = handler.createReply();
                String tmpResult385 = this.absolute_name();
                _out.write_string(tmpResult385);
                break;
            }
            case 5: {
                _out = handler.createReply();
                String tmpResult386 = this.name();
                _out.write_string(tmpResult386);
                break;
            }
            case 6: {
                _out = handler.createReply();
                _out.write_TypeCode(this.type());
                break;
            }
            case 7: {
                _out = handler.createReply();
                this.name(_input.read_string());
                break;
            }
            case 8: {
                _out = handler.createReply();
                this.mode(AttributeModeHelper.read(_input));
                break;
            }
            case 9: {
                _out = handler.createReply();
                AttributeModeHelper.write(_out, this.mode());
                break;
            }
            case 10: {
                _out = handler.createReply();
                ContainerHelper.write(_out, this.defined_in());
                break;
            }
            case 11: {
                _out = handler.createReply();
                String tmpResult387 = this.version();
                _out.write_string(tmpResult387);
                break;
            }
            case 12: {
                _out = handler.createReply();
                this.version(_input.read_string());
                break;
            }
            case 13: {
                _out = handler.createReply();
                DescriptionHelper.write(_out, this.describe());
                break;
            }
            case 14: {
                _out = handler.createReply();
                String tmpResult388 = this.id();
                _out.write_string(tmpResult388);
                break;
            }
            case 15: {
                Container _arg0 = ContainerHelper.read(_input);
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                this.move(_arg0, _arg1, _arg2);
                break;
            }
            case 16: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 17: {
                _out = handler.createReply();
                this.id(_input.read_string());
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("_get_type_def", 0);
        m_opsHash.put("_get_containing_repository", 1);
        m_opsHash.put("_get_def_kind", 2);
        m_opsHash.put("_set_type_def", 3);
        m_opsHash.put("_get_absolute_name", 4);
        m_opsHash.put("_get_name", 5);
        m_opsHash.put("_get_type", 6);
        m_opsHash.put("_set_name", 7);
        m_opsHash.put("_set_mode", 8);
        m_opsHash.put("_get_mode", 9);
        m_opsHash.put("_get_defined_in", 10);
        m_opsHash.put("_get_version", 11);
        m_opsHash.put("_set_version", 12);
        m_opsHash.put("describe", 13);
        m_opsHash.put("_get_id", 14);
        m_opsHash.put("move", 15);
        m_opsHash.put("destroy", 16);
        m_opsHash.put("_set_id", 17);
    }
}

