/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.HashMap;
import org.omg.CORBA.AbstractInterfaceDef;
import org.omg.CORBA.AbstractInterfaceDefHelper;
import org.omg.CORBA.AbstractInterfaceDefSeqHelper;
import org.omg.CORBA.AliasDefHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ConstantDefHelper;
import org.omg.CORBA.ContainedHelper;
import org.omg.CORBA.ContainedSeqHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.ContainerOperations;
import org.omg.CORBA.ContainerPackage.DescriptionSeqHelper;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.EnumDefHelper;
import org.omg.CORBA.EnumMemberSeqHelper;
import org.omg.CORBA.ExceptionDefHelper;
import org.omg.CORBA.ExtInitializer;
import org.omg.CORBA.ExtInitializerSeqHelper;
import org.omg.CORBA.ExtValueDefHelper;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.InitializerSeqHelper;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefHelper;
import org.omg.CORBA.InterfaceDefSeqHelper;
import org.omg.CORBA.LocalInterfaceDefHelper;
import org.omg.CORBA.ModuleDefHelper;
import org.omg.CORBA.NativeDefHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.StructDefHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UnionDefHelper;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.UnionMemberSeqHelper;
import org.omg.CORBA.ValueBoxDefHelper;
import org.omg.CORBA.ValueDef;
import org.omg.CORBA.ValueDefHelper;
import org.omg.CORBA.ValueDefSeqHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class ContainerPOA
extends Servant
implements InvokeHandler,
ContainerOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/Container:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public Container _this() {
        Object __o = this._this_object();
        Container __r = ContainerHelper.narrow(__o);
        return __r;
    }

    public Container _this(ORB orb) {
        Object __o = this._this_object(orb);
        Container __r = ContainerHelper.narrow(__o);
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
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                StructMember[] _arg3 = StructMemberSeqHelper.read(_input);
                _out = handler.createReply();
                StructDefHelper.write(_out, this.create_struct(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 1: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                Any _arg4 = _input.read_any();
                _out = handler.createReply();
                ConstantDefHelper.write(_out, this.create_constant(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 2: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 3: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                _out = handler.createReply();
                AliasDefHelper.write(_out, this.create_alias(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 4: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                UnionMember[] _arg4 = UnionMemberSeqHelper.read(_input);
                _out = handler.createReply();
                UnionDefHelper.write(_out, this.create_union(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 5: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                ContainedHelper.write(_out, this.lookup(_arg0));
                break;
            }
            case 6: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                NativeDefHelper.write(_out, this.create_native(_arg0, _arg1, _arg2));
                break;
            }
            case 7: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                boolean _arg3 = _input.read_boolean();
                boolean _arg4 = _input.read_boolean();
                ValueDef _arg5 = ValueDefHelper.read(_input);
                boolean _arg6 = _input.read_boolean();
                ValueDef[] _arg7 = ValueDefSeqHelper.read(_input);
                InterfaceDef[] _arg8 = InterfaceDefSeqHelper.read(_input);
                ExtInitializer[] _arg9 = ExtInitializerSeqHelper.read(_input);
                _out = handler.createReply();
                ExtValueDefHelper.write(_out, this.create_ext_value(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9));
                break;
            }
            case 8: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                _out = handler.createReply();
                ValueBoxDefHelper.write(_out, this.create_value_box(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 9: {
                String _arg0 = _input.read_string();
                int _arg1 = _input.read_long();
                DefinitionKind _arg2 = DefinitionKindHelper.read(_input);
                boolean _arg3 = _input.read_boolean();
                _out = handler.createReply();
                ContainedSeqHelper.write(_out, this.lookup_name(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 10: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                AbstractInterfaceDef[] _arg3 = AbstractInterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                AbstractInterfaceDefHelper.write(_out, this.create_abstract_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 11: {
                DefinitionKind _arg0 = DefinitionKindHelper.read(_input);
                boolean _arg1 = _input.read_boolean();
                int _arg2 = _input.read_long();
                _out = handler.createReply();
                DescriptionSeqHelper.write(_out, this.describe_contents(_arg0, _arg1, _arg2));
                break;
            }
            case 12: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                ModuleDefHelper.write(_out, this.create_module(_arg0, _arg1, _arg2));
                break;
            }
            case 13: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                boolean _arg3 = _input.read_boolean();
                boolean _arg4 = _input.read_boolean();
                ValueDef _arg5 = ValueDefHelper.read(_input);
                boolean _arg6 = _input.read_boolean();
                ValueDef[] _arg7 = ValueDefSeqHelper.read(_input);
                InterfaceDef[] _arg8 = InterfaceDefSeqHelper.read(_input);
                Initializer[] _arg9 = InitializerSeqHelper.read(_input);
                _out = handler.createReply();
                ValueDefHelper.write(_out, this.create_value(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9));
                break;
            }
            case 14: {
                DefinitionKind _arg0 = DefinitionKindHelper.read(_input);
                boolean _arg1 = _input.read_boolean();
                _out = handler.createReply();
                ContainedSeqHelper.write(_out, this.contents(_arg0, _arg1));
                break;
            }
            case 15: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                String[] _arg3 = EnumMemberSeqHelper.read(_input);
                _out = handler.createReply();
                EnumDefHelper.write(_out, this.create_enum(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 16: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                InterfaceDef[] _arg3 = InterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                InterfaceDefHelper.write(_out, this.create_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 17: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                InterfaceDef[] _arg3 = InterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                LocalInterfaceDefHelper.write(_out, this.create_local_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 18: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 19: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                StructMember[] _arg3 = StructMemberSeqHelper.read(_input);
                _out = handler.createReply();
                ExceptionDefHelper.write(_out, this.create_exception(_arg0, _arg1, _arg2, _arg3));
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
        m_opsHash.put("create_struct", 0);
        m_opsHash.put("create_constant", 1);
        m_opsHash.put("_get_def_kind", 2);
        m_opsHash.put("create_alias", 3);
        m_opsHash.put("create_union", 4);
        m_opsHash.put("lookup", 5);
        m_opsHash.put("create_native", 6);
        m_opsHash.put("create_ext_value", 7);
        m_opsHash.put("create_value_box", 8);
        m_opsHash.put("lookup_name", 9);
        m_opsHash.put("create_abstract_interface", 10);
        m_opsHash.put("describe_contents", 11);
        m_opsHash.put("create_module", 12);
        m_opsHash.put("create_value", 13);
        m_opsHash.put("contents", 14);
        m_opsHash.put("create_enum", 15);
        m_opsHash.put("create_interface", 16);
        m_opsHash.put("create_local_interface", 17);
        m_opsHash.put("destroy", 18);
        m_opsHash.put("create_exception", 19);
    }
}

