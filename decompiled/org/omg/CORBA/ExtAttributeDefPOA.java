/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.ExcDescriptionSeqHelper;
import org.omg.CORBA.ExtAttributeDef;
import org.omg.CORBA.ExtAttributeDefHelper;
import org.omg.CORBA.ExtAttributeDefOperations;
import org.omg.CORBA.ExtAttributeDescriptionHelper;
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

public abstract class ExtAttributeDefPOA
extends Servant
implements InvokeHandler,
ExtAttributeDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/ExtAttributeDef:1.0", "IDL:omg.org/CORBA/AttributeDef:1.0", "IDL:omg.org/CORBA/Contained:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public ExtAttributeDef _this() {
        Object __o = this._this_object();
        ExtAttributeDef __r = ExtAttributeDefHelper.narrow(__o);
        return __r;
    }

    public ExtAttributeDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        ExtAttributeDef __r = ExtAttributeDefHelper.narrow(__o);
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
                ExcDescriptionSeqHelper.write(_out, this.set_exceptions());
                break;
            }
            case 2: {
                _out = handler.createReply();
                RepositoryHelper.write(_out, this.containing_repository());
                break;
            }
            case 3: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 4: {
                _out = handler.createReply();
                this.type_def(IDLTypeHelper.read(_input));
                break;
            }
            case 5: {
                _out = handler.createReply();
                String tmpResult402 = this.absolute_name();
                _out.write_string(tmpResult402);
                break;
            }
            case 6: {
                _out = handler.createReply();
                String tmpResult403 = this.name();
                _out.write_string(tmpResult403);
                break;
            }
            case 7: {
                _out = handler.createReply();
                _out.write_TypeCode(this.type());
                break;
            }
            case 8: {
                _out = handler.createReply();
                this.name(_input.read_string());
                break;
            }
            case 9: {
                _out = handler.createReply();
                this.mode(AttributeModeHelper.read(_input));
                break;
            }
            case 10: {
                _out = handler.createReply();
                this.get_exceptions(ExcDescriptionSeqHelper.read(_input));
                break;
            }
            case 11: {
                _out = handler.createReply();
                AttributeModeHelper.write(_out, this.mode());
                break;
            }
            case 12: {
                _out = handler.createReply();
                ContainerHelper.write(_out, this.defined_in());
                break;
            }
            case 13: {
                _out = handler.createReply();
                String tmpResult404 = this.version();
                _out.write_string(tmpResult404);
                break;
            }
            case 14: {
                _out = handler.createReply();
                this.version(_input.read_string());
                break;
            }
            case 15: {
                _out = handler.createReply();
                DescriptionHelper.write(_out, this.describe());
                break;
            }
            case 16: {
                _out = handler.createReply();
                ExtAttributeDescriptionHelper.write(_out, this.describe_attribute());
                break;
            }
            case 17: {
                _out = handler.createReply();
                String tmpResult405 = this.id();
                _out.write_string(tmpResult405);
                break;
            }
            case 18: {
                Container _arg0 = ContainerHelper.read(_input);
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                this.move(_arg0, _arg1, _arg2);
                break;
            }
            case 19: {
                _out = handler.createReply();
                ExcDescriptionSeqHelper.write(_out, this.get_exceptions());
                break;
            }
            case 20: {
                _out = handler.createReply();
                this.set_exceptions(ExcDescriptionSeqHelper.read(_input));
                break;
            }
            case 21: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 22: {
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
        m_opsHash.put("_get_set_exceptions", 1);
        m_opsHash.put("_get_containing_repository", 2);
        m_opsHash.put("_get_def_kind", 3);
        m_opsHash.put("_set_type_def", 4);
        m_opsHash.put("_get_absolute_name", 5);
        m_opsHash.put("_get_name", 6);
        m_opsHash.put("_get_type", 7);
        m_opsHash.put("_set_name", 8);
        m_opsHash.put("_set_mode", 9);
        m_opsHash.put("_set_get_exceptions", 10);
        m_opsHash.put("_get_mode", 11);
        m_opsHash.put("_get_defined_in", 12);
        m_opsHash.put("_get_version", 13);
        m_opsHash.put("_set_version", 14);
        m_opsHash.put("describe", 15);
        m_opsHash.put("describe_attribute", 16);
        m_opsHash.put("_get_id", 17);
        m_opsHash.put("move", 18);
        m_opsHash.put("_get_get_exceptions", 19);
        m_opsHash.put("_set_set_exceptions", 20);
        m_opsHash.put("destroy", 21);
        m_opsHash.put("_set_id", 22);
    }
}

