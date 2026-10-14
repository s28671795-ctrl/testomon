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
import org.omg.Security.SelectorTypeHelper;
import org.omg.Security.SelectorValue;

public abstract class SelectorValueHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<SelectorValueHelper> clazz = SelectorValueHelper.class;
        synchronized (SelectorValueHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(SelectorValueHelper.id(), "SelectorValue", new StructMember[]{new StructMember("selector", ORB.init().create_alias_tc(SelectorTypeHelper.id(), "SelectorType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("value", ORB.init().get_primitive_tc(TCKind.from_int(11)), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, SelectorValue s2) {
        any.type(SelectorValueHelper.type());
        SelectorValueHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static SelectorValue extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            SelectorValue selectorValue = SelectorValueHelper.read(in);
            return selectorValue;
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
        return "IDL:omg.org/Security/SelectorValue:1.0";
    }

    public static SelectorValue read(InputStream in) {
        SelectorValue result = new SelectorValue();
        result.selector = in.read_ulong();
        result.value = in.read_any();
        return result;
    }

    public static void write(OutputStream out, SelectorValue s2) {
        out.write_ulong(s2.selector);
        out.write_any(s2.value);
    }
}

