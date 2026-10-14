/*
 * Decompiled with CFR 0.152.
 */
package org.omg.GSSUP;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.GSS_NT_ExportedNameHelper;
import org.omg.CSI.UTF8StringHelper;
import org.omg.GSSUP.InitialContextToken;

public abstract class InitialContextTokenHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InitialContextTokenHelper> clazz = InitialContextTokenHelper.class;
        synchronized (InitialContextTokenHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(InitialContextTokenHelper.id(), "InitialContextToken", new StructMember[]{new StructMember("username", ORB.init().create_alias_tc(UTF8StringHelper.id(), "UTF8String", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("password", ORB.init().create_alias_tc(UTF8StringHelper.id(), "UTF8String", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null), new StructMember("target_name", ORB.init().create_alias_tc(GSS_NT_ExportedNameHelper.id(), "GSS_NT_ExportedName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InitialContextToken s2) {
        any.type(InitialContextTokenHelper.type());
        InitialContextTokenHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InitialContextToken extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InitialContextToken initialContextToken = InitialContextTokenHelper.read(in);
            return initialContextToken;
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
        return "IDL:omg.org/GSSUP/InitialContextToken:1.0";
    }

    public static InitialContextToken read(InputStream in) {
        InitialContextToken result = new InitialContextToken();
        result.username = UTF8StringHelper.read(in);
        result.password = UTF8StringHelper.read(in);
        result.target_name = GSS_NT_ExportedNameHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, InitialContextToken s2) {
        UTF8StringHelper.write(out, s2.username);
        UTF8StringHelper.write(out, s2.password);
        GSS_NT_ExportedNameHelper.write(out, s2.target_name);
    }
}

