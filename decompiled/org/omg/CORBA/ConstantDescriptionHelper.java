/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ConstantDescription;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ConstantDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ConstantDescriptionHelper> clazz = ConstantDescriptionHelper.class;
        synchronized (ConstantDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ConstantDescriptionHelper.id(), "ConstantDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("value", ORB.init().get_primitive_tc(TCKind.from_int(11)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ConstantDescription s2) {
        any.type(ConstantDescriptionHelper.type());
        ConstantDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ConstantDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ConstantDescription constantDescription = ConstantDescriptionHelper.read(in);
            return constantDescription;
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
        return "IDL:omg.org/CORBA/ConstantDescription:1.0";
    }

    public static ConstantDescription read(InputStream in) {
        ConstantDescription result = new ConstantDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.type = in.read_TypeCode();
        result.value = in.read_any();
        return result;
    }

    public static void write(OutputStream out, ConstantDescription s2) {
        String tmpResult177 = s2.name;
        out.write_string(tmpResult177);
        String tmpResult178 = s2.id;
        out.write_string(tmpResult178);
        String tmpResult179 = s2.defined_in;
        out.write_string(tmpResult179);
        String tmpResult180 = s2.version;
        out.write_string(tmpResult180);
        out.write_TypeCode(s2.type);
        out.write_any(s2.value);
    }
}

