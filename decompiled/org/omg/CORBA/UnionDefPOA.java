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
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.ContainedSeqHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
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
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.StructDefHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UnionDef;
import org.omg.CORBA.UnionDefHelper;
import org.omg.CORBA.UnionDefOperations;
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

public abstract class UnionDefPOA
extends Servant
implements InvokeHandler,
UnionDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/UnionDef:1.0", "IDL:omg.org/CORBA/Container:1.0", "IDL:omg.org/CORBA/Contained:1.0", "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/TypedefDef:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public UnionDef _this() {
        Object __o = this._this_object();
        UnionDef __r = UnionDefHelper.narrow(__o);
        return __r;
    }

    public UnionDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        UnionDef __r = UnionDefHelper.narrow(__o);
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
            case 1: {
                _out = handler.createReply();
                String tmpResult296 = this.id();
                _out.write_string(tmpResult296);
                break;
            }
            case 2: {
                _out = handler.createReply();
                String tmpResult297 = this.absolute_name();
                _out.write_string(tmpResult297);
                break;
            }
            case 3: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                StructMember[] _arg3 = StructMemberSeqHelper.read(_input);
                _out = handler.createReply();
                StructDefHelper.write(_out, this.create_struct(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 4: {
                DefinitionKind _arg0 = DefinitionKindHelper.read(_input);
                boolean _arg1 = _input.read_boolean();
                int _arg2 = _input.read_long();
                _out = handler.createReply();
                DescriptionSeqHelper.write(_out, this.describe_contents(_arg0, _arg1, _arg2));
                break;
            }
            case 5: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                _out = handler.createReply();
                AliasDefHelper.write(_out, this.create_alias(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 6: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                String[] _arg3 = EnumMemberSeqHelper.read(_input);
                _out = handler.createReply();
                EnumDefHelper.write(_out, this.create_enum(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 7: {
                _out = handler.createReply();
                IDLTypeHelper.write(_out, this.discriminator_type_def());
                break;
            }
            case 8: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                ContainedHelper.write(_out, this.lookup(_arg0));
                break;
            }
            case 9: {
                _out = handler.createReply();
                this.version(_input.read_string());
                break;
            }
            case 10: {
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
            case 11: {
                _out = handler.createReply();
                DescriptionHelper.write(_out, this.describe());
                break;
            }
            case 12: {
                _out = handler.createReply();
                this.name(_input.read_string());
                break;
            }
            case 13: {
                _out = handler.createReply();
                ContainerHelper.write(_out, this.defined_in());
                break;
            }
            case 14: {
                _out = handler.createReply();
                RepositoryHelper.write(_out, this.containing_repository());
                break;
            }
            case 15: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                ModuleDefHelper.write(_out, this.create_module(_arg0, _arg1, _arg2));
                break;
            }
            case 16: {
                Container _arg0 = ContainerHelper.read(_input);
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                this.move(_arg0, _arg1, _arg2);
                break;
            }
            case 17: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                AbstractInterfaceDef[] _arg3 = AbstractInterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                AbstractInterfaceDefHelper.write(_out, this.create_abstract_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 18: {
                _out = handler.createReply();
                String tmpResult298 = this.version();
                _out.write_string(tmpResult298);
                break;
            }
            case 19: {
                _out = handler.createReply();
                _out.write_TypeCode(this.discriminator_type());
                break;
            }
            case 20: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                Any _arg4 = _input.read_any();
                _out = handler.createReply();
                ConstantDefHelper.write(_out, this.create_constant(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 21: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 22: {
                String _arg0 = _input.read_string();
                int _arg1 = _input.read_long();
                DefinitionKind _arg2 = DefinitionKindHelper.read(_input);
                boolean _arg3 = _input.read_boolean();
                _out = handler.createReply();
                ContainedSeqHelper.write(_out, this.lookup_name(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 23: {
                _out = handler.createReply();
                UnionMemberSeqHelper.write(_out, this.members());
                break;
            }
            case 24: {
                _out = handler.createReply();
                _out.write_TypeCode(this.type());
                break;
            }
            case 25: {
                _out = handler.createReply();
                this.id(_input.read_string());
                break;
            }
            case 26: {
                DefinitionKind _arg0 = DefinitionKindHelper.read(_input);
                boolean _arg1 = _input.read_boolean();
                _out = handler.createReply();
                ContainedSeqHelper.write(_out, this.contents(_arg0, _arg1));
                break;
            }
            case 27: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                InterfaceDef[] _arg3 = InterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                LocalInterfaceDefHelper.write(_out, this.create_local_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 28: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 29: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                _out = handler.createReply();
                ValueBoxDefHelper.write(_out, this.create_value_box(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 30: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                UnionMember[] _arg4 = UnionMemberSeqHelper.read(_input);
                _out = handler.createReply();
                UnionDefHelper.write(_out, this.create_union(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 31: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                StructMember[] _arg3 = StructMemberSeqHelper.read(_input);
                _out = handler.createReply();
                ExceptionDefHelper.write(_out, this.create_exception(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 32: {
                _out = handler.createReply();
                this.members(UnionMemberSeqHelper.read(_input));
                break;
            }
            case 33: {
                _out = handler.createReply();
                String tmpResult299 = this.name();
                _out.write_string(tmpResult299);
                break;
            }
            case 34: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                InterfaceDef[] _arg3 = InterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                InterfaceDefHelper.write(_out, this.create_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 35: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                NativeDefHelper.write(_out, this.create_native(_arg0, _arg1, _arg2));
                break;
            }
            case 36: {
                _out = handler.createReply();
                this.discriminator_type_def(IDLTypeHelper.read(_input));
            }
        }
        return _out;
    }

    @Override
    public String[] _all_interfaces(POA poa, byte[] obj_id) {
        return this.ids;
    }

    static {
        m_opsHash.put("create_value", 0);
        m_opsHash.put("_get_id", 1);
        m_opsHash.put("_get_absolute_name", 2);
        m_opsHash.put("create_struct", 3);
        m_opsHash.put("describe_contents", 4);
        m_opsHash.put("create_alias", 5);
        m_opsHash.put("create_enum", 6);
        m_opsHash.put("_get_discriminator_type_def", 7);
        m_opsHash.put("lookup", 8);
        m_opsHash.put("_set_version", 9);
        m_opsHash.put("create_ext_value", 10);
        m_opsHash.put("describe", 11);
        m_opsHash.put("_set_name", 12);
        m_opsHash.put("_get_defined_in", 13);
        m_opsHash.put("_get_containing_repository", 14);
        m_opsHash.put("create_module", 15);
        m_opsHash.put("move", 16);
        m_opsHash.put("create_abstract_interface", 17);
        m_opsHash.put("_get_version", 18);
        m_opsHash.put("_get_discriminator_type", 19);
        m_opsHash.put("create_constant", 20);
        m_opsHash.put("destroy", 21);
        m_opsHash.put("lookup_name", 22);
        m_opsHash.put("_get_members", 23);
        m_opsHash.put("_get_type", 24);
        m_opsHash.put("_set_id", 25);
        m_opsHash.put("contents", 26);
        m_opsHash.put("create_local_interface", 27);
        m_opsHash.put("_get_def_kind", 28);
        m_opsHash.put("create_value_box", 29);
        m_opsHash.put("create_union", 30);
        m_opsHash.put("create_exception", 31);
        m_opsHash.put("_set_members", 32);
        m_opsHash.put("_get_name", 33);
        m_opsHash.put("create_interface", 34);
        m_opsHash.put("create_native", 35);
        m_opsHash.put("_set_discriminator_type_def", 36);
    }
}

