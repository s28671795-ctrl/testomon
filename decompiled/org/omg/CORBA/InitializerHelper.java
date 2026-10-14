/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.StructMemberHelper;
import org.omg.CORBA.StructMemberSeqHelper;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class InitializerHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InitializerHelper> clazz = InitializerHelper.class;
        synchronized (InitializerHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(InitializerHelper.id(), "Initializer", new StructMember[]{new StructMember("members", ORB.init().create_alias_tc(StructMemberSeqHelper.id(), "StructMemberSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(StructMemberHelper.id(), "StructMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null)}))), null), new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Initializer s2) {
        any.type(InitializerHelper.type());
        InitializerHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Initializer extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Initializer initializer = InitializerHelper.read(in);
            return initializer;
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
        return "IDL:omg.org/CORBA/Initializer:1.0";
    }

    public static Initializer read(InputStream in) {
        Initializer result = new Initializer();
        result.members = StructMemberSeqHelper.read(in);
        result.name = in.read_string();
        return result;
    }

    public static void write(OutputStream out, Initializer s2) {
        StructMemberSeqHelper.write(out, s2.members);
        String tmpResult14 = s2.name;
        out.write_string(tmpResult14);
    }
}

