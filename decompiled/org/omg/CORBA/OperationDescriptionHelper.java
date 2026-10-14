/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ContextIdSeqHelper;
import org.omg.CORBA.ContextIdentifierHelper;
import org.omg.CORBA.ExcDescriptionSeqHelper;
import org.omg.CORBA.ExceptionDescriptionHelper;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OperationDescription;
import org.omg.CORBA.OperationModeHelper;
import org.omg.CORBA.ParDescriptionSeqHelper;
import org.omg.CORBA.ParameterDescriptionHelper;
import org.omg.CORBA.ParameterModeHelper;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class OperationDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<OperationDescriptionHelper> clazz = OperationDescriptionHelper.class;
        synchronized (OperationDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(OperationDescriptionHelper.id(), "OperationDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("result", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("mode", ORB.init().create_enum_tc(OperationModeHelper.id(), "OperationMode", new String[]{"OP_NORMAL", "OP_ONEWAY"}), null), new StructMember("contexts", ORB.init().create_alias_tc(ContextIdSeqHelper.id(), "ContextIdSeq", ORB.init().create_sequence_tc(0, ContextIdentifierHelper.type())), null), new StructMember("parameters", ORB.init().create_alias_tc(ParDescriptionSeqHelper.id(), "ParDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ParameterDescriptionHelper.id(), "ParameterDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null), new StructMember("mode", ORB.init().create_enum_tc(ParameterModeHelper.id(), "ParameterMode", new String[]{"PARAM_IN", "PARAM_OUT", "PARAM_INOUT"}), null)}))), null), new StructMember("exceptions", ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)}))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, OperationDescription s2) {
        any.type(OperationDescriptionHelper.type());
        OperationDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static OperationDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            OperationDescription operationDescription = OperationDescriptionHelper.read(in);
            return operationDescription;
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
        return "IDL:omg.org/CORBA/OperationDescription:1.0";
    }

    public static OperationDescription read(InputStream in) {
        OperationDescription result = new OperationDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.result = in.read_TypeCode();
        result.mode = OperationModeHelper.read(in);
        result.contexts = ContextIdSeqHelper.read(in);
        result.parameters = ParDescriptionSeqHelper.read(in);
        result.exceptions = ExcDescriptionSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, OperationDescription s2) {
        String tmpResult418 = s2.name;
        out.write_string(tmpResult418);
        String tmpResult419 = s2.id;
        out.write_string(tmpResult419);
        String tmpResult420 = s2.defined_in;
        out.write_string(tmpResult420);
        String tmpResult421 = s2.version;
        out.write_string(tmpResult421);
        out.write_TypeCode(s2.result);
        OperationModeHelper.write(out, s2.mode);
        ContextIdSeqHelper.write(out, s2.contexts);
        ParDescriptionSeqHelper.write(out, s2.parameters);
        ExcDescriptionSeqHelper.write(out, s2.exceptions);
    }
}

