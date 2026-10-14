/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.ExtensibleFamily;

public abstract class ExtensibleFamilyHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ExtensibleFamilyHelper> clazz = ExtensibleFamilyHelper.class;
        synchronized (ExtensibleFamilyHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(ExtensibleFamilyHelper.id(), "ExtensibleFamily", new StructMember[]{new StructMember("family_definer", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("family", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ExtensibleFamily s2) {
        any.type(ExtensibleFamilyHelper.type());
        ExtensibleFamilyHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ExtensibleFamily extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ExtensibleFamily extensibleFamily = ExtensibleFamilyHelper.read(in);
            return extensibleFamily;
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
        return "IDL:omg.org/Security/ExtensibleFamily:1.0";
    }

    public static ExtensibleFamily read(InputStream in) {
        ExtensibleFamily result = new ExtensibleFamily();
        result.family_definer = in.read_ushort();
        result.family = in.read_ushort();
        return result;
    }

    public static void write(OutputStream out, ExtensibleFamily s2) {
        out.write_ushort(s2.family_definer);
        out.write_ushort(s2.family);
    }
}

