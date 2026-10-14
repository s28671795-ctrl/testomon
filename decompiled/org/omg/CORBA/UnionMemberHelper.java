/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.IDLTypeHelper;
import org.omg.CORBA.IdentifierHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class UnionMemberHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<UnionMemberHelper> clazz = UnionMemberHelper.class;
        synchronized (UnionMemberHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(UnionMemberHelper.id(), "UnionMember", new StructMember[]{new StructMember("name", ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", ORB.init().create_string_tc(0)), null), new StructMember("label", ORB.init().get_primitive_tc(TCKind.from_int(11)), null), new StructMember("type", ORB.init().get_primitive_tc(TCKind.tk_TypeCode), null), new StructMember("type_def", ORB.init().create_interface_tc("IDL:omg.org/CORBA/IDLType:1.0", "IDLType"), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, UnionMember s2) {
        any.type(UnionMemberHelper.type());
        UnionMemberHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UnionMember extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            UnionMember unionMember = UnionMemberHelper.read(in);
            return unionMember;
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
        return "IDL:omg.org/CORBA/UnionMember:1.0";
    }

    public static UnionMember read(InputStream in) {
        UnionMember result = new UnionMember();
        result.name = in.read_string();
        result.label = in.read_any();
        result.type = in.read_TypeCode();
        result.type_def = IDLTypeHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, UnionMember s2) {
        String tmpResult15 = s2.name;
        out.write_string(tmpResult15);
        out.write_any(s2.label);
        out.write_TypeCode(s2.type);
        IDLTypeHelper.write(out, s2.type_def);
    }
}

