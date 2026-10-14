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
import org.omg.Security.ExtensibleFamilyHelper;
import org.omg.Security.Right;

public abstract class RightHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<RightHelper> clazz = RightHelper.class;
        synchronized (RightHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(RightHelper.id(), "Right", new StructMember[]{new StructMember("rights_family", ORB.init().create_struct_tc(ExtensibleFamilyHelper.id(), "ExtensibleFamily", new StructMember[]{new StructMember("family_definer", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("family", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}), null), new StructMember("right", ORB.init().create_string_tc(0), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, Right s2) {
        any.type(RightHelper.type());
        RightHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Right extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            Right right = RightHelper.read(in);
            return right;
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
        return "IDL:omg.org/Security/Right:1.0";
    }

    public static Right read(InputStream in) {
        Right result = new Right();
        result.rights_family = ExtensibleFamilyHelper.read(in);
        result.right = in.read_string();
        return result;
    }

    public static void write(OutputStream out, Right s2) {
        ExtensibleFamilyHelper.write(out, s2.rights_family);
        String tmpResult6 = s2.right;
        out.write_string(tmpResult6);
    }
}

