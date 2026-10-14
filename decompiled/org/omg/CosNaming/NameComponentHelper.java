/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.IstringHelper;
import org.omg.CosNaming.NameComponent;

public abstract class NameComponentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<NameComponentHelper> clazz = NameComponentHelper.class;
        synchronized (NameComponentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(NameComponentHelper.id(), "NameComponent", new StructMember[]{new StructMember("id", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null), new StructMember("kind", ORB.init().create_alias_tc(IstringHelper.id(), "Istring", ORB.init().create_string_tc(0)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, NameComponent s2) {
        any.type(NameComponentHelper.type());
        NameComponentHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NameComponent extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            NameComponent nameComponent = NameComponentHelper.read(in);
            return nameComponent;
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
        return "IDL:omg.org/CosNaming/NameComponent:1.0";
    }

    public static NameComponent read(InputStream in) {
        NameComponent result = new NameComponent();
        result.id = in.read_string();
        result.kind = in.read_string();
        return result;
    }

    public static void write(OutputStream out, NameComponent s2) {
        String tmpResult1 = s2.id;
        out.write_string(tmpResult1);
        String tmpResult2 = s2.kind;
        out.write_string(tmpResult2);
    }
}

