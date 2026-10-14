/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.EnumDef;
import org.omg.CORBA.EnumDefHelper;
import org.omg.CORBA.EnumDefOperations;
import org.omg.CORBA.EnumMemberSeqHelper;
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

public abstract class EnumDefPOA
extends Servant
implements InvokeHandler,
EnumDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/EnumDef:1.0", "IDL:omg.org/CORBA/Contained:1.0", "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/TypedefDef:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public EnumDef _this() {
        Object __o = this._this_object();
        EnumDef __r = EnumDefHelper.narrow(__o);
        return __r;
    }

    public EnumDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        EnumDef __r = EnumDefHelper.narrow(__o);
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
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 1: {
                _out = handler.createReply();
                _out.write_TypeCode(this.type());
                break;
            }
            case 2: {
                _out = handler.createReply();
                String tmpResult305 = this.absolute_name();
                _out.write_string(tmpResult305);
                break;
            }
            case 3: {
                _out = handler.createReply();
                this.id(_input.read_string());
                break;
            }
            case 4: {
                _out = handler.createReply();
                String tmpResult306 = this.name();
                _out.write_string(tmpResult306);
                break;
            }
            case 5: {
                _out = handler.createReply();
                String tmpResult307 = this.version();
                _out.write_string(tmpResult307);
                break;
            }
            case 6: {
                _out = handler.createReply();
                DescriptionHelper.write(_out, this.describe());
                break;
            }
            case 7: {
                _out = handler.createReply();
                this.version(_input.read_string());
                break;
            }
            case 8: {
                _out = handler.createReply();
                String tmpResult308 = this.id();
                _out.write_string(tmpResult308);
                break;
            }
            case 9: {
                _out = handler.createReply();
                RepositoryHelper.write(_out, this.containing_repository());
                break;
            }
            case 10: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 11: {
                _out = handler.createReply();
                ContainerHelper.write(_out, this.defined_in());
                break;
            }
            case 12: {
                Container _arg0 = ContainerHelper.read(_input);
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                this.move(_arg0, _arg1, _arg2);
                break;
            }
            case 13: {
                _out = handler.createReply();
                this.name(_input.read_string());
                break;
            }
            case 14: {
                _out = handler.createReply();
                EnumMemberSeqHelper.write(_out, this.members());
                break;
            }
            case 15: {
                _out = handler.createReply();
                this.members(EnumMemberSeqHelper.read(_input));
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("_get_def_kind", 0);
        m_opsHash.put("_get_type", 1);
        m_opsHash.put("_get_absolute_name", 2);
        m_opsHash.put("_set_id", 3);
        m_opsHash.put("_get_name", 4);
        m_opsHash.put("_get_version", 5);
        m_opsHash.put("describe", 6);
        m_opsHash.put("_set_version", 7);
        m_opsHash.put("_get_id", 8);
        m_opsHash.put("_get_containing_repository", 9);
        m_opsHash.put("destroy", 10);
        m_opsHash.put("_get_defined_in", 11);
        m_opsHash.put("move", 12);
        m_opsHash.put("_set_name", 13);
        m_opsHash.put("_get_members", 14);
        m_opsHash.put("_set_members", 15);
    }
}

