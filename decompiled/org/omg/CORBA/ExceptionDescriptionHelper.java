/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ExceptionDescription;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ExceptionDescriptionHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExceptionDescriptionHelper> clazz = ExceptionDescriptionHelper.class;
        synchronized (ExceptionDescriptionHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ExceptionDescriptionHelper.id(), "ExceptionDescription", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("id", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("defined_in", ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", ORB.init().create_string_tc(0)), null), new StructMember("version", ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExceptionDescription s2) {
        any.type(ExceptionDescriptionHelper.type());
        ExceptionDescriptionHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ExceptionDescription extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ExceptionDescription exceptionDescription = ExceptionDescriptionHelper.read(in);
            return exceptionDescription;
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
        return "IDL:omg.org/CORBA/ExceptionDescription:1.0";
    }

    public static ExceptionDescription read(InputStream in) {
        ExceptionDescription result = new ExceptionDescription();
        result.name = in.read_string();
        result.id = in.read_string();
        result.defined_in = in.read_string();
        result.version = in.read_string();
        result.type = in.read_TypeCode();
        return result;
    }

    public static void write(OutputStream out, ExceptionDescription s2) {
        String tmpResult16 = s2.name;
        out.write_string(tmpResult16);
        String tmpResult17 = s2.id;
        out.write_string(tmpResult17);
        String tmpResult18 = s2.defined_in;
        out.write_string(tmpResult18);
        String tmpResult19 = s2.version;
        out.write_string(tmpResult19);
        out.write_TypeCode(s2.type);
    }
}

