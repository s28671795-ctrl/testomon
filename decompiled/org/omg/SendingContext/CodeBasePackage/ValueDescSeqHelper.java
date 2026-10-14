/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SendingContext.CodeBasePackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.AttrDescriptionSeqHelper;
import org.omg.CORBA.AttributeDescriptionHelper;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.ContextIdentifierHelper;
import org.omg.CORBA.ExcDescriptionSeqHelper;
import org.omg.CORBA.ExceptionDescriptionHelper;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.InitializerHelper;
import org.omg.CORBA.InitializerSeqHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OpDescriptionSeqHelper;
import org.omg.CORBA.OperationDescriptionHelper;
import org.omg.CORBA.OperationModeHelper;
import org.omg.CORBA.ParDescriptionSeqHelper;
import org.omg.CORBA.ParameterDescriptionHelper;
import org.omg.CORBA.ParameterModeHelper;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.RepositoryIdSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberHelper;
import org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.omg.CORBA.ValueMemberHelper;
import org.omg.CORBA.ValueMemberSeqHelper;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.VisibilityHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueDescSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, FullValueDescription[] s2) {
        any.type(ValueDescSeqHelper.type());
        ValueDescSeqHelper.write(any.create_output_stream(), s2);
    }

    public static FullValueDescription[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ValueDescSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueDescSeqHelper> clazz = ValueDescSeqHelper.class;
        synchronized (ValueDescSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ValueDescSeqHelper.id(), "ValueDescSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(FullValueDescriptionHelper.id(), "FullValueDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("is_abstract", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("is_custom", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("operations", ORB.init().create_alias_tc(OpDescriptionSeqHelper.id(), "OpDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(OperationDescriptionHelper.id(), "OperationDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("result", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(OperationModeHelper.id(), "OperationMode", new String[]{"OP_NORMAL", "OP_ONEWAY"}), null), new StructMember("contexts", ORB.init().create_alias_tc(ContextIdSeqHelper.id(), "ContextIdSeq", ORB.init().create_sequence_tc(0, ContextIdentifierHelper.type())), null), new StructMember("parameters", ORB.init().create_alias_tc(ParDescriptionSeqHelper.id(), "ParDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ParameterDescriptionHelper.id(), "ParameterDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("mode", ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"}), null)}))), null), new StructMember("exceptions", ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)}))), null)}))), null), new StructMember("attributes", ORB.init().create_alias_tc(AttrDescriptionSeqHelper.id(), "AttrDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(AttributeDescriptionHelper.id(), "AttributeDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(AttributeModeHelper.id(), "AttributeMode", new String[]{"ATTR_NORMAL", "ATTR_READONLY"}), null)}))), null), new StructMember("members", ORB.init().create_alias_tc(ValueMemberSeqHelper.id(), "ValueMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ValueMemberHelper.id(), "ValueMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("access", ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)}))), null), new StructMember("initializers", ORB.init().create_alias_tc(InitializerSeqHelper.id(), "InitializerSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(InitializerHelper.id(), "Initializer", new StructMember[]{new StructMember("members", ORB.init().create_alias_tc(StructMemberSeqHelper.id(), "StructMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(StructMemberHelper.id(), "StructMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null)}))), null), new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null)}))), null), new StructMember("supported_interfaces", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null), new StructMember("abstract_base_values", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null), new StructMember("is_truncatable", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("base_value", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/SendingContext/CodeBase/ValueDescSeq:1.0";
    }

    public static FullValueDescription[] read(InputStream _in) {
        int _l_result1 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result1 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result1);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        FullValueDescription[] _result = new FullValueDescription[_l_result1];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = FullValueDescriptionHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, FullValueDescription[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            FullValueDescriptionHelper.write(_out, _s[i]);
        }
    }
}

