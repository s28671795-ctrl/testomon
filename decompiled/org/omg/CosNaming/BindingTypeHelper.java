/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.BindingType;

public abstract class BindingTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<BindingTypeHelper> clazz = BindingTypeHelper.class;
        synchronized (BindingTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_enum_tc(BindingTypeHelper.id(), "BindingType", new String[]{"nobject", "ncontext"});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, BindingType s2) {
        any.type(BindingTypeHelper.type());
        BindingTypeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BindingType extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            BindingType bindingType = BindingTypeHelper.read(in);
            return bindingType;
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
        return "IDL:omg.org/CosNaming/BindingType:1.0";
    }

    public static BindingType read(InputStream in) {
        return BindingType.from_int(in.read_long());
    }

    public static void write(OutputStream out, BindingType s2) {
        out.write_long(s2.value());
    }
}

