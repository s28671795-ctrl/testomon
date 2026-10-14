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
import org.omg.CORBA.AttributeDefHelper;
import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ConstantDefHelper;
import org.omg.CORBA.ContainedHelper;
import org.omg.CORBA.ContainedPackage.DescriptionHelper;
import org.omg.CORBA.ContainedSeqHelper;
import org.omg.CORBA.Container;
import org.omg.CORBA.ContainerHelper;
import org.omg.CORBA.ContainerPackage.DescriptionSeqHelper;
import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.EnumDefHelper;
import org.omg.CORBA.EnumMemberSeqHelper;
import org.omg.CORBA.ExceptionDef;
import org.omg.CORBA.ExceptionDefHelper;
import org.omg.CORBA.ExceptionDefSeqHelper;
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
import org.omg.CORBA.OperationDefHelper;
import org.omg.CORBA.OperationMode;
import org.omg.CORBA.OperationModeHelper;
import org.omg.CORBA.ParDescriptionSeqHelper;
import org.omg.CORBA.ParameterDescription;
import org.omg.CORBA.RepositoryHelper;
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
import org.omg.CORBA.ValueDefOperations;
import org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.omg.CORBA.ValueDefSeqHelper;
import org.omg.CORBA.ValueMemberDefHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public abstract class ValueDefPOA
extends Servant
implements InvokeHandler,
ValueDefOperations {
    private static final HashMap<String, Integer> m_opsHash = new HashMap();
    private String[] ids = new String[]{"IDL:omg.org/CORBA/ValueDef:1.0", "IDL:omg.org/CORBA/Container:1.0", "IDL:omg.org/CORBA/Contained:1.0", "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/IRObject:1.0"};

    public ValueDef _this() {
        Object __o = this._this_object();
        ValueDef __r = ValueDefHelper.narrow(__o);
        return __r;
    }

    public ValueDef _this(ORB orb) {
        Object __o = this._this_object(orb);
        ValueDef __r = ValueDefHelper.narrow(__o);
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
                this.is_truncatable(_input.read_boolean());
                break;
            }
            case 2: {
                _out = handler.createReply();
                String tmpResult633 = this.id();
                _out.write_string(tmpResult633);
                break;
            }
            case 3: {
                _out = handler.createReply();
                String tmpResult634 = this.absolute_name();
                _out.write_string(tmpResult634);
                break;
            }
            case 4: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                _out.write_boolean(this.is_a(_arg0));
                break;
            }
            case 5: {
                _out = handler.createReply();
                this.abstract_base_values(ValueDefSeqHelper.read(_input));
                break;
            }
            case 6: {
                _out = handler.createReply();
                InitializerSeqHelper.write(_out, this.initializers());
                break;
            }
            case 7: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                StructMember[] _arg3 = StructMemberSeqHelper.read(_input);
                _out = handler.createReply();
                StructDefHelper.write(_out, this.create_struct(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 8: {
                DefinitionKind _arg0 = DefinitionKindHelper.read(_input);
                boolean _arg1 = _input.read_boolean();
                int _arg2 = _input.read_long();
                _out = handler.createReply();
                DescriptionSeqHelper.write(_out, this.describe_contents(_arg0, _arg1, _arg2));
                break;
            }
            case 9: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                _out = handler.createReply();
                AliasDefHelper.write(_out, this.create_alias(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 10: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                String[] _arg3 = EnumMemberSeqHelper.read(_input);
                _out = handler.createReply();
                EnumDefHelper.write(_out, this.create_enum(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 11: {
                String _arg0 = _input.read_string();
                _out = handler.createReply();
                ContainedHelper.write(_out, this.lookup(_arg0));
                break;
            }
            case 12: {
                _out = handler.createReply();
                ValueDefHelper.write(_out, this.base_value());
                break;
            }
            case 13: {
                _out = handler.createReply();
                this.version(_input.read_string());
                break;
            }
            case 14: {
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
            case 15: {
                _out = handler.createReply();
                DescriptionHelper.write(_out, this.describe());
                break;
            }
            case 16: {
                _out = handler.createReply();
                this.name(_input.read_string());
                break;
            }
            case 17: {
                _out = handler.createReply();
                _out.write_boolean(this.is_abstract());
                break;
            }
            case 18: {
                _out = handler.createReply();
                ContainerHelper.write(_out, this.defined_in());
                break;
            }
            case 19: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                OperationMode _arg4 = OperationModeHelper.read(_input);
                ParameterDescription[] _arg5 = ParDescriptionSeqHelper.read(_input);
                ExceptionDef[] _arg6 = ExceptionDefSeqHelper.read(_input);
                String[] _arg7 = ContextIdSeqHelper.read(_input);
                _out = handler.createReply();
                OperationDefHelper.write(_out, this.create_operation(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7));
                break;
            }
            case 20: {
                _out = handler.createReply();
                _out.write_boolean(this.is_custom());
                break;
            }
            case 21: {
                _out = handler.createReply();
                RepositoryHelper.write(_out, this.containing_repository());
                break;
            }
            case 22: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                ModuleDefHelper.write(_out, this.create_module(_arg0, _arg1, _arg2));
                break;
            }
            case 23: {
                Container _arg0 = ContainerHelper.read(_input);
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                this.move(_arg0, _arg1, _arg2);
                break;
            }
            case 24: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                AbstractInterfaceDef[] _arg3 = AbstractInterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                AbstractInterfaceDefHelper.write(_out, this.create_abstract_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 25: {
                _out = handler.createReply();
                this.base_value(ValueDefHelper.read(_input));
                break;
            }
            case 26: {
                _out = handler.createReply();
                String tmpResult635 = this.version();
                _out.write_string(tmpResult635);
                break;
            }
            case 27: {
                _out = handler.createReply();
                this.supported_interfaces(InterfaceDefSeqHelper.read(_input));
                break;
            }
            case 28: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                short _arg4 = _input.read_short();
                _out = handler.createReply();
                ValueMemberDefHelper.write(_out, this.create_value_member(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 29: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                Any _arg4 = _input.read_any();
                _out = handler.createReply();
                ConstantDefHelper.write(_out, this.create_constant(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 30: {
                _out = handler.createReply();
                this.destroy();
                break;
            }
            case 31: {
                String _arg0 = _input.read_string();
                int _arg1 = _input.read_long();
                DefinitionKind _arg2 = DefinitionKindHelper.read(_input);
                boolean _arg3 = _input.read_boolean();
                _out = handler.createReply();
                ContainedSeqHelper.write(_out, this.lookup_name(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 32: {
                _out = handler.createReply();
                _out.write_TypeCode(this.type());
                break;
            }
            case 33: {
                _out = handler.createReply();
                this.id(_input.read_string());
                break;
            }
            case 34: {
                DefinitionKind _arg0 = DefinitionKindHelper.read(_input);
                boolean _arg1 = _input.read_boolean();
                _out = handler.createReply();
                ContainedSeqHelper.write(_out, this.contents(_arg0, _arg1));
                break;
            }
            case 35: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                InterfaceDef[] _arg3 = InterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                LocalInterfaceDefHelper.write(_out, this.create_local_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 36: {
                _out = handler.createReply();
                DefinitionKindHelper.write(_out, this.def_kind());
                break;
            }
            case 37: {
                _out = handler.createReply();
                ValueDefSeqHelper.write(_out, this.abstract_base_values());
                break;
            }
            case 38: {
                _out = handler.createReply();
                this.is_custom(_input.read_boolean());
                break;
            }
            case 39: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                _out = handler.createReply();
                ValueBoxDefHelper.write(_out, this.create_value_box(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 40: {
                _out = handler.createReply();
                _out.write_boolean(this.is_truncatable());
                break;
            }
            case 41: {
                _out = handler.createReply();
                this.initializers(InitializerSeqHelper.read(_input));
                break;
            }
            case 42: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                AttributeMode _arg4 = AttributeModeHelper.read(_input);
                _out = handler.createReply();
                AttributeDefHelper.write(_out, this.create_attribute(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 43: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                IDLType _arg3 = IDLTypeHelper.read(_input);
                UnionMember[] _arg4 = UnionMemberSeqHelper.read(_input);
                _out = handler.createReply();
                UnionDefHelper.write(_out, this.create_union(_arg0, _arg1, _arg2, _arg3, _arg4));
                break;
            }
            case 44: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                StructMember[] _arg3 = StructMemberSeqHelper.read(_input);
                _out = handler.createReply();
                ExceptionDefHelper.write(_out, this.create_exception(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 45: {
                _out = handler.createReply();
                String tmpResult636 = this.name();
                _out.write_string(tmpResult636);
                break;
            }
            case 46: {
                _out = handler.createReply();
                InterfaceDefSeqHelper.write(_out, this.supported_interfaces());
                break;
            }
            case 47: {
                _out = handler.createReply();
                FullValueDescriptionHelper.write(_out, this.describe_value());
                break;
            }
            case 48: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                InterfaceDef[] _arg3 = InterfaceDefSeqHelper.read(_input);
                _out = handler.createReply();
                InterfaceDefHelper.write(_out, this.create_interface(_arg0, _arg1, _arg2, _arg3));
                break;
            }
            case 49: {
                _out = handler.createReply();
                this.is_abstract(_input.read_boolean());
                break;
            }
            case 50: {
                String _arg0 = _input.read_string();
                String _arg1 = _input.read_string();
                String _arg2 = _input.read_string();
                _out = handler.createReply();
                NativeDefHelper.write(_out, this.create_native(_arg0, _arg1, _arg2));
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
        m_opsHash.put("create_value", 0);
        m_opsHash.put("_set_is_truncatable", 1);
        m_opsHash.put("_get_id", 2);
        m_opsHash.put("_get_absolute_name", 3);
        m_opsHash.put("is_a", 4);
        m_opsHash.put("_set_abstract_base_values", 5);
        m_opsHash.put("_get_initializers", 6);
        m_opsHash.put("create_struct", 7);
        m_opsHash.put("describe_contents", 8);
        m_opsHash.put("create_alias", 9);
        m_opsHash.put("create_enum", 10);
        m_opsHash.put("lookup", 11);
        m_opsHash.put("_get_base_value", 12);
        m_opsHash.put("_set_version", 13);
        m_opsHash.put("create_ext_value", 14);
        m_opsHash.put("describe", 15);
        m_opsHash.put("_set_name", 16);
        m_opsHash.put("_get_is_abstract", 17);
        m_opsHash.put("_get_defined_in", 18);
        m_opsHash.put("create_operation", 19);
        m_opsHash.put("_get_is_custom", 20);
        m_opsHash.put("_get_containing_repository", 21);
        m_opsHash.put("create_module", 22);
        m_opsHash.put("move", 23);
        m_opsHash.put("create_abstract_interface", 24);
        m_opsHash.put("_set_base_value", 25);
        m_opsHash.put("_get_version", 26);
        m_opsHash.put("_set_supported_interfaces", 27);
        m_opsHash.put("create_value_member", 28);
        m_opsHash.put("create_constant", 29);
        m_opsHash.put("destroy", 30);
        m_opsHash.put("lookup_name", 31);
        m_opsHash.put("_get_type", 32);
        m_opsHash.put("_set_id", 33);
        m_opsHash.put("contents", 34);
        m_opsHash.put("create_local_interface", 35);
        m_opsHash.put("_get_def_kind", 36);
        m_opsHash.put("_get_abstract_base_values", 37);
        m_opsHash.put("_set_is_custom", 38);
        m_opsHash.put("create_value_box", 39);
        m_opsHash.put("_get_is_truncatable", 40);
        m_opsHash.put("_set_initializers", 41);
        m_opsHash.put("create_attribute", 42);
        m_opsHash.put("create_union", 43);
        m_opsHash.put("create_exception", 44);
        m_opsHash.put("_get_name", 45);
        m_opsHash.put("_get_supported_interfaces", 46);
        m_opsHash.put("describe_value", 47);
        m_opsHash.put("create_interface", 48);
        m_opsHash.put("_set_is_abstract", 49);
        m_opsHash.put("create_native", 50);
    }
}

