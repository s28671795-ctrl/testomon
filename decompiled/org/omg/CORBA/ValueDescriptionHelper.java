/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.RepositoryIdSeqHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueDescription;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ValueDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ValueDescriptionHelper> clazz = ValueDescriptionHelper.class;
        synchronized (ValueDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ValueDescriptionHelper.id(), "ValueDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("is_abstract", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("is_custom", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("supported_interfaces", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null), new StructMember("abstract_base_values", ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", ORB.init().create_sequence_tc(0, RepositoryIdHelper.type())), null), new StructMember("is_truncatable", ORB.init().get_primitive_tc(TCKind.from_int(8)), null), new StructMember("base_value", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ValueDescription s2) {
        any.type(ValueDescriptionHelper.type());
        ValueDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ValueDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ValueDescription valueDescription = ValueDescriptionHelper.read(in);
            return valueDescription;
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
        return "IDL:omg.org/CORBA/ValueDescription:1.0";
    }

    public static ValueDescription read(InputStream in) {
        ValueDescription result = new ValueDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.is_abstract = in.read_boolean();
        result.is_custom = in.read_boolean();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.supported_interfaces = RepositoryIdSeqHelper.read(in);
        result.abstract_base_values = RepositoryIdSeqHelper.read(in);
        result.is_truncatable = in.read_boolean();
        result.base_value = in.read_string();
        return result;
    }

    public static void write(OutputStream out, ValueDescription s2) {
        String tmpResult642 = s2.name;
        out.write_string(tmpResult642);
        String tmpResult643 = s2.id;
        out.write_string(tmpResult643);
        out.write_boolean(s2.is_abstract);
        out.write_boolean(s2.is_custom);
        String tmpResult644 = s2.defined_in;
        out.write_string(tmpResult644);
        String tmpResult645 = s2.version;
        out.write_string(tmpResult645);
        RepositoryIdSeqHelper.write(out, s2.supported_interfaces);
        RepositoryIdSeqHelper.write(out, s2.abstract_base_values);
        out.write_boolean(s2.is_truncatable);
        String tmpResult646 = s2.base_value;
        out.write_string(tmpResult646);
    }
}

