/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA;

import org.jboss.com.sun.org.omg.CORBA.ContextIdSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.ContextIdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.ExcDescriptionSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.ExceptionDescriptionHelper;
import org.jboss.com.sun.org.omg.CORBA.IdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.OperationDescription;
import org.jboss.com.sun.org.omg.CORBA.OperationModeHelper;
import org.jboss.com.sun.org.omg.CORBA.ParDescriptionSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.ParameterDescriptionHelper;
import org.jboss.com.sun.org.omg.CORBA.RepositoryIdHelper;
import org.jboss.com.sun.org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class OperationDescriptionHelper {
    private static String _id = "IDL:omg.org/CORBA/OperationDescription:1.0";
    private static TypeCode __typeCode = null;
    private static boolean __active = false;

    public static void insert(Any a, OperationDescription that) {
        OutputStream out = a.create_output_stream();
        a.type(OperationDescriptionHelper.type());
        OperationDescriptionHelper.write(out, that);
        a.read_value(out.create_input_stream(), OperationDescriptionHelper.type());
    }

    public static OperationDescription extract(Any a) {
        return OperationDescriptionHelper.read(a.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static synchronized TypeCode type() {
        if (__typeCode != null) return __typeCode;
        Class<TypeCode> clazz = TypeCode.class;
        synchronized (TypeCode.class) {
            if (__typeCode != null) return __typeCode;
            if (__active) {
                // ** MonitorExit[var0] (shouldn't be in output)
                return ORB.init().create_recursive_tc(_id);
            }
            __active = true;
            StructMember[] _members0 = new StructMember[9];
            TypeCode _tcOf_members0 = null;
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", _tcOf_members0);
            _members0[0] = new StructMember("name", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _members0[1] = new StructMember("id", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _members0[2] = new StructMember("defined_in", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", _tcOf_members0);
            _members0[3] = new StructMember("version", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
            _members0[4] = new StructMember("result", _tcOf_members0, null);
            _tcOf_members0 = OperationModeHelper.type();
            _members0[5] = new StructMember("mode", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(ContextIdentifierHelper.id(), "ContextIdentifier", _tcOf_members0);
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(ContextIdSeqHelper.id(), "ContextIdSeq", _tcOf_members0);
            _members0[6] = new StructMember("contexts", _tcOf_members0, null);
            _tcOf_members0 = ParameterDescriptionHelper.type();
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(ParDescriptionSeqHelper.id(), "ParDescriptionSeq", _tcOf_members0);
            _members0[7] = new StructMember("parameters", _tcOf_members0, null);
            _tcOf_members0 = ExceptionDescriptionHelper.type();
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(ExcDescriptionSeqHelper.id(), "ExcDescriptionSeq", _tcOf_members0);
            _members0[8] = new StructMember("exceptions", _tcOf_members0, null);
            __typeCode = ORB.init().create_struct_tc(OperationDescriptionHelper.id(), "OperationDescription", _members0);
            __active = false;
            // ** MonitorExit[var0] (shouldn't be in output)
            return __typeCode;
        }
    }

    public static String id() {
        return _id;
    }

    public static OperationDescription read(InputStream istream) {
        OperationDescription value = new OperationDescription();
        value.name = istream.read_string();
        value.id = istream.read_string();
        value.defined_in = istream.read_string();
        value.version = istream.read_string();
        value.result = istream.read_TypeCode();
        value.mode = OperationModeHelper.read(istream);
        value.contexts = ContextIdSeqHelper.read(istream);
        value.parameters = ParDescriptionSeqHelper.read(istream);
        value.exceptions = ExcDescriptionSeqHelper.read(istream);
        return value;
    }

    public static void write(OutputStream ostream, OperationDescription value) {
        ostream.write_string(value.name);
        ostream.write_string(value.id);
        ostream.write_string(value.defined_in);
        ostream.write_string(value.version);
        ostream.write_TypeCode(value.result);
        OperationModeHelper.write(ostream, value.mode);
        ContextIdSeqHelper.write(ostream, value.contexts);
        ParDescriptionSeqHelper.write(ostream, value.parameters);
        ExcDescriptionSeqHelper.write(ostream, value.exceptions);
    }
}

