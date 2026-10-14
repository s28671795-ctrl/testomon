/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.ExtValueDefPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.ContextIdentifierHelper;
import org.omg.CORBA.ExcDescriptionSeqHelper;
import org.omg.CORBA.ExceptionDescriptionHelper;
import org.omg.CORBA.ExtAttrDescriptionSeqHelper;
import org.omg.CORBA.ExtAttributeDescriptionHelper;
import org.omg.CORBA.ExtInitializerHelper;
import org.omg.CORBA.ExtInitializerSeqHelper;
import org.omg.CORBA.ExtValueDefPackage.ExtFullValueDescription;
import org.omg.CORBA.IdentifierHelper;
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
import org.omg.CORBA.ValueMemberHelper;
import org.omg.CORBA.ValueMemberSeqHelper;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.VisibilityHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExtFullValueDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExtFullValueDescriptionHelper> clazz = ExtFullValueDescriptionHelper.class;
        synchronized (ExtFullValueDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ExtFullValueDescriptionHelper.id(), "ExtFullValueDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("is_abstract", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("is_custom", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("operations", ORB.init().create_alias_tc(OpDescriptionSeqHelper.id(), "OpDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(OperationDescriptionHelper.id(), "OperationDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("result", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(OperationModeHelper.id(), "OperationMode", new String[]{"OP_NORMAL", "OP_ONEWAY"}), null), new StructMember("contexts", ORB.init().create_alias_tc(ContextIdSeqHelper.id(), "ContextIdSeq", ORB.init().create_sequence_tc(0, ContextIdentifierHelper.type())), null), new StructMember("parameters", ORB.init().create_alias_tc(ParDescriptionSeqHelper.id(), "ParDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ParameterDescriptionHelper.id(), "ParameterDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("mode", ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"}), null)}))), null), new StructMember("exceptions", ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)}))), null)}))), null), new StructMember("attributes", ORB.init().create_alias_tc(ExtAttrDescriptionSeqHelper.id(), "ExtAttrDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExtAttributeDescriptionHelper.id(), "ExtAttributeDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(AttributeModeHelper.id(), "AttributeMode", new String[]{"ATTR_NORMAL", "ATTR_READONLY"}), null), new StructMember("get_exceptions", ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)}))), null), new StructMember("put_exceptions", ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)}))), null)}))), null), new StructMember("members", ORB.init().create_alias_tc(ValueMemberSeqHelper.id(), "ValueMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ValueMemberHelper.id(), "ValueMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("access", ORB.init().create_alias_tc(VisibilityHelper.id(), "Visibility", ORB.init().get_primitive_tc(TCKind.from_int(2))), null)}))), null), new StructMember("initializers", ORB.init().create_alias_tc(ExtInitializerSeqHelper.id(), "ExtInitializerSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExtInitializerHelper.id(), "ExtInitializer", new StructMember[]{new StructMember("members", ORB.init().create_alias_tc(StructMemberSeqHelper.id(), "StructMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(StructMemberHelper.id(), "StructMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null)}))), null), new StructMember("exceptions", ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)}))), null), new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null)}))), null), new StructMember("supported_interfaces", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null), new StructMember("abstract_base_values", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null), new StructMember("is_truncatable", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("base_value", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExtFullValueDescription s2) {
        any.type(ExtFullValueDescriptionHelper.type());
        ExtFullValueDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ExtFullValueDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ExtFullValueDescription extFullValueDescription = ExtFullValueDescriptionHelper.read(in);
            return extFullValueDescription;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ExtValueDef/ExtFullValueDescription:1.0";
    }

    public static ExtFullValueDescription read(InputStream in) {
        ExtFullValueDescription result = new ExtFullValueDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.is_abstract = in.read_boolean();
        result.is_custom = in.read_boolean();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.operations = OpDescriptionSeqHelper.read(in);
        result.attributes = ExtAttrDescriptionSeqHelper.read(in);
        result.members = ValueMemberSeqHelper.read(in);
        result.initializers = ExtInitializerSeqHelper.read(in);
        result.supported_interfaces = RepositoryIdSeqHelper.read(in);
        result.abstract_base_values = RepositoryIdSeqHelper.read(in);
        result.is_truncatable = in.read_boolean();
        result.base_value = in.read_string();
        result.type = in.read_TypeCode();
        return result;
    }

    public static void write(OutputStream out, ExtFullValueDescription s2) {
        String tmpResult713 = s2.name;
        out.write_string(tmpResult713);
        String tmpResult714 = s2.id;
        out.write_string(tmpResult714);
        out.write_boolean(s2.is_abstract);
        out.write_boolean(s2.is_custom);
        String tmpResult715 = s2.defined_in;
        out.write_string(tmpResult715);
        String tmpResult716 = s2.version;
        out.write_string(tmpResult716);
        OpDescriptionSeqHelper.write(out, s2.operations);
        ExtAttrDescriptionSeqHelper.write(out, s2.attributes);
        ValueMemberSeqHelper.write(out, s2.members);
        ExtInitializerSeqHelper.write(out, s2.initializers);
        RepositoryIdSeqHelper.write(out, s2.supported_interfaces);
        RepositoryIdSeqHelper.write(out, s2.abstract_base_values);
        out.write_boolean(s2.is_truncatable);
        String tmpResult717 = s2.base_value;
        out.write_string(tmpResult717);
        out.write_TypeCode(s2.type);
    }
}

