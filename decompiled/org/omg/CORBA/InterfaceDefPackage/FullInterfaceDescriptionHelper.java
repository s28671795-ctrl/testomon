/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.InterfaceDefPackage;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.AttrDescriptionSeqHelper;
import org.omg.CORBA.AttributeDescriptionHelper;
import org.omg.CORBA.AttributeModeHelper;
import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.ContextIdentifierHelper;
import org.omg.CORBA.ExcDescriptionSeqHelper;
import org.omg.CORBA.ExceptionDescriptionHelper;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.InterfaceDefPackage.FullInterfaceDescription;
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
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class FullInterfaceDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<FullInterfaceDescriptionHelper> clazz = FullInterfaceDescriptionHelper.class;
        synchronized (FullInterfaceDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(FullInterfaceDescriptionHelper.id(), "FullInterfaceDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("operations", ORB.init().create_alias_tc(OpDescriptionSeqHelper.id(), "OpDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(OperationDescriptionHelper.id(), "OperationDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("result", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(OperationModeHelper.id(), "OperationMode", new String[]{"OP_NORMAL", "OP_ONEWAY"}), null), new StructMember("contexts", ORB.init().create_alias_tc(ContextIdSeqHelper.id(), "ContextIdSeq", ORB.init().create_sequence_tc(0, ContextIdentifierHelper.type())), null), new StructMember("parameters", ORB.init().create_alias_tc(ParDescriptionSeqHelper.id(), "ParDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ParameterDescriptionHelper.id(), "ParameterDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("mode", ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"}), null)}))), null), new StructMember("exceptions", ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)}))), null)}))), null), new StructMember("attributes", ORB.init().create_alias_tc(AttrDescriptionSeqHelper.id(), "AttrDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(AttributeDescriptionHelper.id(), "AttributeDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(AttributeModeHelper.id(), "AttributeMode", new String[]{"ATTR_NORMAL", "ATTR_READONLY"}), null)}))), null), new StructMember("base_interfaces", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, FullInterfaceDescription s2) {
        any.type(FullInterfaceDescriptionHelper.type());
        FullInterfaceDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static FullInterfaceDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            FullInterfaceDescription fullInterfaceDescription = FullInterfaceDescriptionHelper.read(in);
            return fullInterfaceDescription;
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
        return "IDL:omg.org/CORBA/InterfaceDef/FullInterfaceDescription:1.0";
    }

    public static FullInterfaceDescription read(InputStream in) {
        FullInterfaceDescription result = new FullInterfaceDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.operations = OpDescriptionSeqHelper.read(in);
        result.attributes = AttrDescriptionSeqHelper.read(in);
        result.base_interfaces = RepositoryIdSeqHelper.read(in);
        result.type = in.read_TypeCode();
        return result;
    }

    public static void write(OutputStream out, FullInterfaceDescription s2) {
        String tmpResult483 = s2.name;
        out.write_string(tmpResult483);
        String tmpResult484 = s2.id;
        out.write_string(tmpResult484);
        String tmpResult485 = s2.defined_in;
        out.write_string(tmpResult485);
        String tmpResult486 = s2.version;
        out.write_string(tmpResult486);
        OpDescriptionSeqHelper.write(out, s2.operations);
        AttrDescriptionSeqHelper.write(out, s2.attributes);
        RepositoryIdSeqHelper.write(out, s2.base_interfaces);
        out.write_TypeCode(s2.type);
    }
}

